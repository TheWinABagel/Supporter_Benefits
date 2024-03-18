package dev.bagel.benefits;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerManager {

    public static List<GameProfile> PLAYERS = new ArrayList<>();

    public static void init() {

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            if (SBConfig.enable_URL_loading) {
                new Thread(() -> {
                    SupporterBenefits.LOGGER.info("Loading supporter data...");
                    try {
                        URL url = new URL(SBConfig.custom_URL);
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                            String s;
                            GameProfileCache cache = server.getProfileCache();
                            if (cache == null) {
                                SupporterBenefits.LOGGER.error("Server cache is null! Not sure why...");
                                return;
                            }
                            while ((s = reader.readLine()) != null) {
                                Optional<GameProfile> profileOptional = cache.get(s);
                                if (profileOptional.isPresent()) {
                                    PLAYERS.add(profileOptional.get());
                                } else {
                                    SupporterBenefits.LOGGER.error("Player name {} is not valid!", s);
                                }
                            }
                            reader.close();
                        } catch (IOException ex) {
                            SupporterBenefits.LOGGER.error("Exception loading supporter data!");
                            ex.printStackTrace();
                        }
                    } catch (Exception k) {
                        SupporterBenefits.LOGGER.error("File at {} does not seem to exist!", SBConfig.custom_URL);
                    }
                    SupporterBenefits.LOGGER.info("Loaded {} supporter names from URL.", PLAYERS.size());
                }, "Supporter Benefits").start();
            }
            initConfigPlayers(server);
        });
    }

    private static void initConfigPlayers(MinecraftServer server) {
        List<GameProfile> profileList = new ArrayList<>();
        GameProfileCache cache = server.getProfileCache();
        if (cache == null) {
            SupporterBenefits.LOGGER.error("Server cache is null! Not sure why...");
            return;
        }
        for (String s : SBConfig.custom_players) {
            Optional<GameProfile> profileOptional = cache.get(s);
            if (profileOptional.isPresent()) {
                profileList.add(profileOptional.get());
            } else {
                SupporterBenefits.LOGGER.error("Player name {} is not valid!", s);
            }
        }
        SupporterBenefits.LOGGER.info("Loaded {} supporter names from config.", SBConfig.custom_players.size());
        PLAYERS.addAll(profileList);
    }
}
