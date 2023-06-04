package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ServiceRequest {

    @GET("api/service/list")
    Call<CommonResponse<List<Service>>> list(@Header("uid") Integer uid);
}
