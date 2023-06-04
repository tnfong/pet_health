package com.example.quanlypet.retrofit;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.MessageGroup;
import com.example.quanlypet.model.MessageInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MessageInfoRequest {

    @GET("api/message/info/list")
    Call<CommonResponse<List<MessageInfo>>> list(@Header("uid") Integer uid, @Query("id_group") Integer idGroup);
}
