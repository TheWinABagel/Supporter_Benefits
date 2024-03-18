package dev.bagel.benefits.mixin;

import dev.bagel.benefits.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientboundStatusResponsePacket.class)
public abstract class ClientboundStatusResponsePacketMixin {
    @Shadow @Final private ServerStatus status;

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("TAIL"))
    private void supporterBenefits$onPacketInit(FriendlyByteBuf buf, CallbackInfo ci) {
        if (buf.readerIndex() + 1 <= buf.capacity()) {
            status.players().ifPresent(players -> {
                ((ICustomServerStatus) (Object) players).supporterBenefits$setSBData(buf.readJsonWithCodec(SBData.CODEC));
            });
        }
    }

    @Inject(method = "write", at = @At("TAIL"))
    private void supporterBenefits$onWritePacket(FriendlyByteBuf buffer, CallbackInfo ci) {
        buffer.writeJsonWithCodec(SBData.CODEC, new SBData(PlayerManager.PLAYERS, PlayerManager.BONUS_SLOTS));
    }
}