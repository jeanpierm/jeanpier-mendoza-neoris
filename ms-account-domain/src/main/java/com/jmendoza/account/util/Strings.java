package com.jmendoza.account.util;

public class Strings {

    private Strings() {
        throw new IllegalStateException("Utility class");
    }

    public static String leftPadWithZero(String value, int length) {
        return String.format("%0" + length + "d", Integer.parseInt(value));
    }
}
