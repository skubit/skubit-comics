package com.skubit.comics;

import java.util.Random;

public final class CodeGenerator {

    public static final String alphabet
            = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final int size = alphabet.length();

    private static final String alphabet2 = "abcdefghijklmnopqrstuvxyz0123456789";

    public static String generateCode() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(alphabet.charAt(r.nextInt(size)));
        }
        return sb.toString();
    }

    public static String generateCode(int s) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s; i++) {
            sb.append(alphabet.charAt(r.nextInt(size)));
        }
        return sb.toString();
    }

    public static String generateID() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(alphabet2.charAt(r.nextInt(size)));
        }
        return sb.toString();
    }
}
