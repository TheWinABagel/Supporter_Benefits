package dev.bagel.benefits.mixin;

import com.mojang.authlib.GameProfile;
import dev.bagel.benefits.PlayerManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.List;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {

    @Shadow @Final private List<ServerPlayer> players;

    @Shadow @Final protected int maxPlayers;

    @Inject(method = "canPlayerLogin", cancellable = true, at = @At(value = "RETURN"),
            slice = @Slice(from = @At(value = "INVOKE", target = "java/util/List.size ()I"), to = @At(value = "TAIL"))
    )
    private void supporterBenefits$modifyIfPlayerCanLogin(SocketAddress socketAddress, GameProfile gameProfile, CallbackInfoReturnable<Component> cir) {
        if (PlayerManager.PLAYERS.contains(gameProfile)) {
            if (this.players.size() < (this.maxPlayers + PlayerManager.BONUS_SLOTS)) {
                cir.setReturnValue(null);
            }
        }
    }
}
