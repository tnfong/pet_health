package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.MessageGroup;
import com.example.quanlypet.model.MessageInfo;
import com.example.quanlypet.retrofit.MessageGroupRequest;
import com.example.quanlypet.retrofit.MessageInfoRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageInfoRepository {
    private final String TAG = this.getClass().getName();
    private final MessageInfoRequest messageInfoRequest;

    public MessageInfoRepository(){
        this.messageInfoRequest = RetrofitRequest.instance().create(MessageInfoRequest.class);
    }

    public LiveData<CommonResponse<List<MessageInfo>>> list(Integer uid, Integer idGroup){
        MutableLiveData<CommonResponse<List<MessageInfo>>> data = new MutableLiveData<>();
        try{
            this.messageInfoRequest.list(uid, idGroup).enqueue(new Callback<CommonResponse<List<MessageInfo>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<MessageInfo>>> call, Response<CommonResponse<List<MessageInfo>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<MessageInfo>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
