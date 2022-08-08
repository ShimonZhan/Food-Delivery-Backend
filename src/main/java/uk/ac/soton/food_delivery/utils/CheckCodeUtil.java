package uk.ac.soton.food_delivery.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

public class CheckCodeUtil {
    //    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final String SYMBOLS = "0123456789";

    private static final Random RANDOM = new SecureRandom();

    public static String getCheckCode() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            sb.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));
        }

        return sb.toString();
    }

    public static LocalDateTime getCheckCodeExpireTime() {
        return LocalDateTime.now().plusMinutes(30);
    }
}
