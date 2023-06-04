package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.Analysis;
import com.example.quanlypet.model.MessageGroup;
import com.example.quanlypet.retrofit.AnalysisRequest;
import com.example.quanlypet.retrofit.MessageGroupRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageGroupRepository {
    private final String TAG = this.getClass().getName();
    private final MessageGroupRequest messageGroupRequest;

    public MessageGroupRepository(){
        this.messageGroupRequest = RetrofitRequest.instance().create(MessageGroupRequest.class);
    }

    public LiveData<CommonResponse<List<MessageGroup>>> list(Integer uid){
        MutableLiveData<CommonResponse<List<MessageGroup>>> data = new MutableLiveData<>();
        try{
            this.messageGroupRequest.list(uid).enqueue(new Callback<CommonResponse<List<MessageGroup>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<MessageGroup>>> call, Response<CommonResponse<List<MessageGroup>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<MessageGroup>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<Integer>> create(Integer uid, Integer idUser){
        MutableLiveData<CommonResponse<Integer>> data = new MutableLiveData<>();
        try{
            this.messageGroupRequest.create(uid, idUser).enqueue(new Callback<CommonResponse<Integer>>() {
                @Override
                public void onResponse(Call<CommonResponse<Integer>> call, Response<CommonResponse<Integer>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Integer>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
