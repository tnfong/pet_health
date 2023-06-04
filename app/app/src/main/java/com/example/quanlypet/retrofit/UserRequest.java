package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserRequest {

    @GET("api/user/list")
    Call<CommonResponse<List<User>>> list(@Header("uid") Integer uid, @Query("id_role") Integer idRole);

    @GET("api/user/{id}")
    Call<CommonResponse<User>> getById(@Header("uid") Integer uid, @Path("id") Integer id);

    @POST("api/user/changePass")
    Call<CommonResponse<User>> changePass(@Header("uid") Integer uid, @Query("password") String password);

    @Multipart
    @POST("api/user/save")
    Call<CommonResponse<User>> save(@Header("uid") Integer uid
            , @Part("username") RequestBody username
            , @Part("password") RequestBody password
            , @Part("full_name") RequestBody fullName
            , @Part("email") RequestBody email
            , @Part("phone") RequestBody phone
            , @Part("gender") RequestBody gender
            , @Part("id_role") RequestBody idRole
            , @Part("avatar_url") RequestBody avatarUrl
            , @Part("specialize") RequestBody specialize
            , @Part MultipartBody.Part image);

    @GET("api/user/info")
    Call<CommonResponse<User>> info(@Header("uid") Integer uid);

    @DELETE("api/user/{id}")
    Call<CommonResponse<Boolean>> delete(@Header("uid") Integer uid, @Path("id") Integer id);
}
