package com.prime.common.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class GenerateUtil {

    public static final String CAPITAL_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String SPECIAL_CHARACTERS = "!@#$";
    public static final String NUMBERS = "0123456789";

    public static String randomPassword(int length) {
        String combinedChars = CAPITAL_CASE_LETTERS + LOWER_CASE_LETTERS + SPECIAL_CHARACTERS + NUMBERS;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = LOWER_CASE_LETTERS.charAt(random.nextInt(LOWER_CASE_LETTERS.length()));
        password[1] = CAPITAL_CASE_LETTERS.charAt(random.nextInt(CAPITAL_CASE_LETTERS.length()));
        password[2] = SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length()));
        password[3] = NUMBERS.charAt(random.nextInt(NUMBERS.length()));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return String.valueOf(password);
    }

    public static String createUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static String appendZeroPrefix(int id, int length) {
        String customerNumber = String.valueOf(id);
        if (customerNumber.length() >= length) {
            return customerNumber;
        }
        String zeroFormat = new StringBuilder("%0").append(length).append("d").toString();
        customerNumber = String.format(zeroFormat, id);
        return customerNumber;
    }

    public static String randomCharacters(int length) {
        SecureRandom rnd = new SecureRandom();
        var allPatterns = NUMBERS + CAPITAL_CASE_LETTERS + LOWER_CASE_LETTERS;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(allPatterns.charAt(rnd.nextInt(allPatterns.length())));
        }

        return sb.toString();
    }
}
