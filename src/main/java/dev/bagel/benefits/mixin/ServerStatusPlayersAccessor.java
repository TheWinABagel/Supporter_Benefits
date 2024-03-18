package dev.bagel.benefits.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import net.minecraft.network.protocol.status.ServerStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerStatus.Players.class)
public interface ServerStatusPlayersAccessor {
    @Accessor
    static Codec<GameProfile> getPROFILE_CODEC() {
        throw new UnsupportedOperationException();
    }
}
