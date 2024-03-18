package dev.bagel.benefits;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.ArrayList;
import java.util.List;

public class SBConfig extends MidnightConfig {
    public static final String ENABLE_PACKETS_COMMENT = "Enables client functionality. Disable if you want vanilla clients to be able to see player list properly.";
    public static final String URL_STRING_COMMENT = "URL for a list of players. Use this if you have a website or github repository you would like to link to.";
    public static final String URL_ENABLE_COMMENT = "List of players. Can use either this or the URL.";
    public static final String PLAYERS_COMMENT = "List of players. Can use either this or the URL.";
    public static final String BONUS_SLOTS_COMMENT = "Amount of bonus slots available.";
    @Entry(name = ENABLE_PACKETS_COMMENT) public static boolean enable_sending_packets = true;
    @Entry(name = URL_STRING_COMMENT) public static String custom_URL = "https://raw.githubusercontent.com/TheWinABagel/Supporter_Benefits/master/Players.txt";
    @Entry(name = URL_ENABLE_COMMENT) public static boolean enable_URL_loading = false;
    @Entry(name = PLAYERS_COMMENT) public static List<String> custom_players = new ArrayList<>();
    @Entry(name = BONUS_SLOTS_COMMENT, min = 0) public static int additional_slots = 0;
}
