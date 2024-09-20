package com.dev.pos.util.security;

import org.mindrot.BCrypt;

public class PasswordManager {

    public static String encrypt(String userPassword) {
        return BCrypt.hashpw(userPassword, BCrypt.gensalt(10));
    }

    public static boolean checkPassword(String userPassword, String hashedPassword) {
        return BCrypt.checkpw(userPassword, hashedPassword);
    }

}
