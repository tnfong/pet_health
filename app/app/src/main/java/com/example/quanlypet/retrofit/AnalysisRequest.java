package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Analysis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AnalysisRequest {

    @GET("api/analysis/month")
    Call<CommonResponse<List<Analysis>>> month(@Header("uid") Integer uid);

    @GET("api/analysis/week")
    Call<CommonResponse<List<Analysis>>> week(@Header("uid") Integer uid);
}
