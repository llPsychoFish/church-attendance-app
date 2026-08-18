package com.example.myfirsttimer.util;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.SecureRandom;

public final class PasswordHasher {
    private PasswordHasher() {
    }

    private static final String ALGO = "SHA-256";
    private static final int SALT_BYTES = 16;

    /** Returns "base64(salt):base64(hash)" — safe to store in the Usher table. */
    public static String hash(String pin) {
        try {
            byte[] salt = new byte[SALT_BYTES];
            new SecureRandom().nextBytes(salt);
            MessageDigest md = MessageDigest.getInstance(ALGO);
            md.update(salt);
            byte[] hash = md.digest(pin.getBytes("UTF-8"));
            String saltB64 = Base64.encodeToString(salt, Base64.NO_WRAP);
            String hashB64 = Base64.encodeToString(hash, Base64.NO_WRAP);
            return saltB64 + ":" + hashB64;
        } catch (Exception e) {
            throw new RuntimeException("PIN hashing failed", e);
        }
    }

    public static boolean verify(String pin, String stored) {
        if (stored == null || !stored.contains(":")) {
            return false;
        }
        try {
            String[] parts = stored.split(":", 2);
            byte[] salt = Base64.decode(parts[0], Base64.NO_WRAP);
            byte[] expected = Base64.decode(parts[1], Base64.NO_WRAP);
            MessageDigest md = MessageDigest.getInstance(ALGO);
            md.update(salt);
            byte[] actual = md.digest(pin.getBytes("UTF-8"));
            return MessageDigest.isEqual(expected, actual);
        } catch (Exception e) {
            return false;
        }
    }
}
