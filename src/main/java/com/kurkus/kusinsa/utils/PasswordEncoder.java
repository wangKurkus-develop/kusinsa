package com.kurkus.kusinsa.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean matches(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
