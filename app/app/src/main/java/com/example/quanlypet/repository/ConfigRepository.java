package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.Analysis;
import com.example.quanlypet.model.Config;
import com.example.quanlypet.retrofit.AnalysisRequest;
import com.example.quanlypet.retrofit.ConfigRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConfigRepository {
    private final String TAG = this.getClass().getName();
    private final ConfigRequest configRequest;

    public ConfigRepository(){
        this.configRequest = RetrofitRequest.instance().create(ConfigRequest.class);
    }

    public LiveData<CommonResponse<Config>> get(Integer uid, String key){
        MutableLiveData<CommonResponse<Config>> data = new MutableLiveData<>();
        try{
            this.configRequest.get(uid, key).enqueue(new Callback<CommonResponse<Config>>() {
                @Override
                public void onResponse(Call<CommonResponse<Config>> call, Response<CommonResponse<Config>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Config>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
