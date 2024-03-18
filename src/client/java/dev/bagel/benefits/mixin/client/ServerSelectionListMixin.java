package dev.bagel.benefits.mixin.client;

import dev.bagel.benefits.ICustomServerStatus;
import dev.bagel.benefits.SBData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;

@Mixin(ServerSelectionList.OnlineServerEntry.class)
public class ServerSelectionListMixin {
    @Unique private int SB$increasedMax;
    @Unique private Component SB$patreonSizeComponent;
    @Shadow @Final private ServerData serverData;

    @Shadow @Final private JoinMultiplayerScreen screen;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "java/lang/Boolean.booleanValue ()Z"))
    private void SB$renderTooltip(GuiGraphics gfx, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean bl, float partialTick, CallbackInfo ci){
        int m = mouseX - left;
        int n = mouseY - top;
        if (this.SB$patreonSizeComponent != null && m >= width - 30 && m <= width - 17 && n >= 0 && n <= 8) {
            Component comp = Component.translatable("supporter_benefits.info.slot_info", this.SB$increasedMax);
            this.screen.setToolTip(Collections.singletonList(comp));
        }
    }

    @Inject(method = "render", at = @At(value = "HEAD"))
    private void addRender(GuiGraphics gfx, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean bl, float partialTick, CallbackInfo ci) {
        if (this.serverData.players == null) return;
        SBData data = ((ICustomServerStatus) (Object) this.serverData.players).supporterBenefits$getSBData();
        if (data == null) return;
        this.SB$increasedMax = data.increasedMax();
        this.SB$patreonSizeComponent = Component.translatable("supporter_benefits.info.slot_count", this.SB$increasedMax).withStyle(ChatFormatting.GOLD);
        gfx.drawString(this.minecraft.font, this.SB$patreonSizeComponent, left + width - this.minecraft.font.width(this.SB$patreonSizeComponent) - 15 - 2, top + 1, 0);

    }

    @ModifyArg(method = "render", slice = @Slice(from = @At(value = "INVOKE", target = "net/minecraft/network/chat/MutableComponent.withStyle (Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;")),
            at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.drawString (Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I", ordinal = 0),
            index = 2
    )
    private int SB$movePlayerCountRendering(int x) {
        return SB$modifyLocation(x);
    }

    @ModifyConstant(method = "render", slice = @Slice(from =
            @At(value = "INVOKE", target = "net/minecraft/client/gui/screens/multiplayer/ServerSelectionList$OnlineServerEntry.drawIcon (Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/resources/ResourceLocation;)V")),
            constant = @Constant(intValue = 15)
    )
    private int SB$movePlayerCountTooltip(int x) {
        return SB$modifyLocation(x);
    }

    @Unique
    private int SB$modifyLocation(int x) {
        if (this.SB$increasedMax == 0) return x;
        return x - this.minecraft.font.width(this.SB$patreonSizeComponent) - 1;
    }
}
