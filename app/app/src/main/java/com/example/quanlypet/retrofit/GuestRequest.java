package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.model.Guest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GuestRequest {

    @GET("api/guest/list")
    Call<CommonResponse<List<Guest>>> list(@Header("uid") Integer uid, @Query("id_animal") Integer idAnimal);

    @POST("api/guest/save")
    Call<CommonResponse<Guest>> save(@Header("uid") Integer uid, @Body Guest guest);
}
