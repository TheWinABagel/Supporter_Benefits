package dev.bagel.benefits;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import java.util.List;

public record SBData(List<GameProfile> profiles, int increasedMax) {
    public static final Codec<SBData> CODEC = RecordCodecBuilder.create(inst -> inst.group(ExtraCodecs.GAME_PROFILE.listOf().fieldOf("profiles").forGetter(SBData::profiles), Codec.INT.fieldOf("increasedMax").forGetter(SBData::increasedMax)).apply(inst, SBData::new));
}
