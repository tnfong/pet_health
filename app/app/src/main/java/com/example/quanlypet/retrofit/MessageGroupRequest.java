package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.MessageGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MessageGroupRequest {

    @GET("api/message/group/list")
    Call<CommonResponse<List<MessageGroup>>> list(@Header("uid") Integer uid);

    @POST("api/message/group/create")
    Call<CommonResponse<Integer>> create(@Header("uid") Integer uid, @Part("id_user") Integer idUser);
}
