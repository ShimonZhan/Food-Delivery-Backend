package uk.ac.soton.food_delivery.utils;

import java.util.Random;

/**
 * @author ShimonZhan
 * @since 2022-04-27 02:28:22
 */
public class PhoneNumGenerator {
    public static String generate() {
        int num1, num2, num3;
        int set2, set3;
        Random generator = new Random();
        num1 = generator.nextInt(7) + 1;
        num2 = generator.nextInt(8);
        num3 = generator.nextInt(8);
        set2 = generator.nextInt(643) + 100;
        set3 = generator.nextInt(8999) + 1000;

        return "" + num1 + num2 + num3 + set2 + set3;
    }

    public static void main(String[] args) {
        System.out.println(generate());
    }
}
