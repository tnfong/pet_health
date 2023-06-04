package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Analysis;
import com.example.quanlypet.model.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ConfigRequest {

    @GET("api/config")
    Call<CommonResponse<Config>> get(@Header("uid") Integer uid, @Query("key") String key);
}
