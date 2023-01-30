package com.nextvoyager.conferences.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncoder {

    private PasswordEncoder() {}

    public static String hash(String inputPassword) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 32, 64);
        char[] password = inputPassword.toCharArray();

        try {
            return argon2.hash(2,15*1024,1, password);
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }

    }

    public static boolean check(String inputPassword, String hash) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 32, 64);
        char[] password = inputPassword.toCharArray();

        try {
            return argon2.verify(hash, password);
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
    }
}
