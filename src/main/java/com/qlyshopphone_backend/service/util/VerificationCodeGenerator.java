package com.qlyshopphone_backend.service.util;

import java.security.SecureRandom;

public class VerificationCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateCode(){
        char[] code = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            code[i] = CHARACTERS.charAt(index);
        }
        return new String(code);
    }
}
