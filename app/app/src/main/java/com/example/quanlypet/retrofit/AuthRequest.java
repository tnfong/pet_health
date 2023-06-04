package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface AuthRequest {

    @POST("api/auth/login")
    Call<CommonResponse<String>> login(@Body User user);

    @Multipart
    @POST("api/auth/register")
    Call<CommonResponse<User>> register(@Part("username") RequestBody username
            , @Part("password") RequestBody password
            , @Part("full_name") RequestBody fullName
            , @Part("phone") RequestBody phone
            , @Part("mail") RequestBody mail
            , @Part("gender") RequestBody gender);

}
