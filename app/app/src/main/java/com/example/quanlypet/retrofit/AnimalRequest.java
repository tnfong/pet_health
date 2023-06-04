package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Animal;

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

public interface AnimalRequest {

    @GET("api/animal/list")
    Call<CommonResponse<List<Animal>>> list(@Header("uid") Integer uid);

    @GET("api/animal/{id}")
    Call<CommonResponse<Animal>> getById(@Header("uid") Integer uid, @Path("id") Integer id);

    @Multipart
    @POST("api/animal/save")
    Call<CommonResponse<Animal>> save(@Header("uid") Integer uid
            , @Part("id") RequestBody id
            , @Part("avatar_url") RequestBody avatarUrl
            , @Part("name") RequestBody name
            , @Part("age") RequestBody age
            , @Part("species") RequestBody species
            , @Part("id_status") RequestBody idStatus
            , @Part("description") RequestBody description
            , @Part MultipartBody.Part image);
}
