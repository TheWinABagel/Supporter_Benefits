package dev.bagel.benefits;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class PlayerManager {

    public static List<String> PLAYER_NAMES = new ArrayList<>();
    public static String URL_STRING = "https://raw.githubusercontent.com/TheWinABagel/FakerLib/master/TestWings.txt";
    public static void init() {
        new Thread(() -> {
            SupporterBenefits.LOGGER.info("Loading patreon wing data...");
            try {
                URL url = new URL(URL_STRING);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    String s;
                    while ((s = reader.readLine()) != null) {
                        String[] split = s.split(" ", 2);
                        if (split.length != 2) {
                            SupporterBenefits.LOGGER.error("Invalid patreon wing entry {} will be ignored.", s);
                            continue;
                        }
                        PLAYER_NAMES.add(split[0]);
                    }
                    reader.close();
                }
                catch (IOException ex) {
                    SupporterBenefits.LOGGER.error("Exception loading patreon wing data!");
                    ex.printStackTrace();
                }
            }
            catch (Exception k) {
                SupporterBenefits.LOGGER.error("File at ");
            }
            SupporterBenefits.LOGGER.info("Loaded {} patreon wings.", PLAYER_NAMES.size());
        }, "Placebo (FakerLib) Patreon Wing Loader").start();
    }
}
