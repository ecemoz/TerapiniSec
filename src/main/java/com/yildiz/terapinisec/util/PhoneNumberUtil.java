package com.yildiz.terapinisec.util;

import java.util.regex.Pattern;

public class PhoneNumberUtil {

    private static final String phoneRegex = "^\\+?[0-9]{10,15}$";
    private static final Pattern phonePattern = Pattern.compile(phoneRegex);

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber ==null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        return phonePattern.matcher(phoneNumber).matches();
    }
}