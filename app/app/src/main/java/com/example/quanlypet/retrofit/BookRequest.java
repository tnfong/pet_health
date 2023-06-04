package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.Book;

import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookRequest {

    @GET("api/book/list")
    Call<CommonResponse<List<Book>>> list(@Header("uid") Integer uid, @Query("id_status") Integer idStatus);

    @GET("api/book/{id}")
    Call<CommonResponse<Book>> info(@Header("uid") Integer uid, @Path("id") Integer id);

    @Multipart
    @POST("api/book/save")
    Call<CommonResponse<Book>> save(@Header("uid") Integer uid
            , @Part("id") RequestBody id
            , @Part("id_doctor") RequestBody idDoctor
            , @Part("id_animal") RequestBody idAnimal
            , @Part("id_status") RequestBody idStatus
            , @Part("photo_url") RequestBody photoUrl
            , @Part("time") RequestBody time
            , @Part("time_hold") RequestBody timeHold
            , @Part("description") RequestBody description
            , @Part("id_service") RequestBody service
            , @Part MultipartBody.Part image);
}
