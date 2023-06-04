package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Bill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BillRequest {

    @GET("api/bill/list")
    Call<CommonResponse<List<Bill>>> list(@Header("uid") Integer uid);

    @GET("api/bill/init")
    Call<CommonResponse<Bill>> init(@Header("uid") Integer uid, @Query("id_book") Integer idBook);

    @POST("api/bill/save")
    Call<CommonResponse<Bill>> save(@Header("uid") Integer uid, @Body Bill bill);
}
