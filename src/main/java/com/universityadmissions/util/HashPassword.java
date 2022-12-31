package com.universityadmissions.util;

import org.mindrot.jbcrypt.BCrypt;

public class HashPassword {
    private HashPassword() {

    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
