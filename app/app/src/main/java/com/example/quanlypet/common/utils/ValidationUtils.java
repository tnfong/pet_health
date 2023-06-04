package com.example.quanlypet.common.utils;

public class ValidationUtils {

    public static boolean isEmpty(String str){
        return str.isEmpty();
    }

    public static boolean isPhone(String str){
        String regexPhoneNumber = "(09|03|07|08|05)+([0-9]{8})";
        return str.matches(regexPhoneNumber);
    }

    public static boolean isMail(String str){
        String regexPhoneNumber = "^(.+)@(.+)$";
        return str.matches(regexPhoneNumber);
    }
}
