package com.example.quanlypet.common;

import com.example.quanlypet.model.User;

public class DataStatic {

    public static final String STACK_APP = "PET_MANAGEMENT";
    private static final String IP = "192.168.0.102";
    public static final String BASE_WS = "ws://"+IP+":8081";
    public static final String BASE_URL = "http://"+IP+":8081/";
//    public static final String BASE_URL = "http://192.168.154.132:8081/";

    public static class HttpStatus{
        public static final int SUCCESS = 200;
        public static final int BAD_REQUEST = 400;
    }

    public static class SESSION {
        public static final String NAME = "SESSION_PET";
        public static User USER_LOGIN = null;
        public static class KEY{
            public static final String AUTH = "AUTH";
            public static final String USER_ID = "USER_ID";

            public static final String USERNAME = "USERNAME";
            public static final String PASSWORD = "PASSWORD";
            public static final String REMEMBER_ACCOUNT = "REMEMBER_ACCOUNT";
        }
    }

    public static class INTENT{
        public static final String ID = "ID";
        public static final String ID_ANIMAL = "ID_ANIMAL";
        public static final String ID_BOOK = "ID_BOOK";
    }

    public static class CODE {
        public static final Integer CHOOSE_IMAGE = 102;
    }
}
