package com.example.quanlypet.common.utils;

import android.util.Log;

import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

public class RestUtils<T> {

    public static <T> CommonResponse<T> get(Response<CommonResponse<T>> response){
        if (response.raw().code() != DataStatic.HttpStatus.SUCCESS && response.errorBody() != null){

        }else if (response.body() != null){
            return response.body();
        }
        return null;
    }
}
