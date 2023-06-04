package com.example.quanlypet.common.utils;

import com.example.quanlypet.common.DataStatic;

public class UserUtils {

    public static boolean isAdmin(){
        if(DataStatic.SESSION.USER_LOGIN == null) return false;
        return DataStatic.SESSION.USER_LOGIN.getIdRole().equals(1);
    }

    public static boolean isDoctor(){
        if(DataStatic.SESSION.USER_LOGIN == null) return false;
        return DataStatic.SESSION.USER_LOGIN.getIdRole().equals(2);
    }

    public static boolean isUser(){
        if(DataStatic.SESSION.USER_LOGIN == null) return false;
        return DataStatic.SESSION.USER_LOGIN.getIdRole().equals(3);
    }
}
