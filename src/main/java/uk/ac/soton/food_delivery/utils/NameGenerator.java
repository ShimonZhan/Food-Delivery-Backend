package uk.ac.soton.food_delivery.utils;

import java.util.Random;

/**
 * @author ShimonZhan
 * @since 2022-04-27 02:19:56
 */
public class NameGenerator {

    private static final String[] Beginning = {"Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk"};
    private static final String[] Middle = {"air", "ir", "mi", "sor", "mee", "clo",
            "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
            "marac", "zoir", "slamar", "salmar", "urak"};
    private static final String[] End = {"d", "ed", "ark", "arc", "es", "er", "der",
            "tron", "med", "ure", "zur", "cred", "mur"};

    private static final Random rand = new Random();

    public static String generateName() {

        return Beginning[rand.nextInt(Beginning.length)] +
                Middle[rand.nextInt(Middle.length)] +
                End[rand.nextInt(End.length)];

    }

    public static void main(String[] args) {
        System.out.println(generateName());
    }

}
