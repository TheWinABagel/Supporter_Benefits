package dev.bagel.benefits.mixin;

import dev.bagel.benefits.ICustomServerStatus;
import dev.bagel.benefits.SBData;
import net.minecraft.network.protocol.status.ServerStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerStatus.Players.class)
public class ServerStatusPlayersMixin implements ICustomServerStatus {
    @Unique
    SBData SB$sbData;

    @Override
    public void supporterBenefits$setSBData(SBData data) {
        this.SB$sbData = data;
    }

    @Override
    public SBData supporterBenefits$getSBData() {
        return SB$sbData;
    }
}
