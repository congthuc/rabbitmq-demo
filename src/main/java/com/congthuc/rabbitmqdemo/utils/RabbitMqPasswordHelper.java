package com.congthuc.rabbitmqdemo.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Author: pct
 * 5/13/2019
 */
public class RabbitMqPasswordHelper {

    public static void main(String[] args) {
        String password_hash = getPasswordHash("admin123579");
        System.out.println(password_hash);
    }

    /**
     * Generates a salted SHA-256 hash of a given password.
     */
    private static String getPasswordHash(String password) {
        byte[] salt = getSalt();
        try {
            byte[] saltedPassword = concatenateByteArray(salt, password.getBytes(
                    StandardCharsets.UTF_8));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltedPassword);

            return Base64
                    .getEncoder().encodeToString(concatenateByteArray(salt,hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Generates a 32 bit random salt.
     */
    private static byte[] getSalt() {
        byte[] ba = new byte[4];
        new SecureRandom().nextBytes(ba);
        return ba;
    }

    /**
     * Concatenates two byte arrays.
     */
    private static byte[] concatenateByteArray(byte[] a, byte[] b) {
        int lenA = a.length;
        int lenB = b.length;
        byte[] c = Arrays.copyOf(a, lenA + lenB);
        System.arraycopy(b, 0, c, lenA, lenB);
        return c;
    }
}
