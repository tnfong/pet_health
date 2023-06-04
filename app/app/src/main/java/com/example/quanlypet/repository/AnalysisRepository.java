package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.Analysis;
import com.example.quanlypet.retrofit.AnalysisRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnalysisRepository {
    private final String TAG = this.getClass().getName();
    private final AnalysisRequest analysisRequest;

    public AnalysisRepository(){
        this.analysisRequest = RetrofitRequest.instance().create(AnalysisRequest.class);
    }

    public LiveData<CommonResponse<List<Analysis>>> month(Integer uid){
        MutableLiveData<CommonResponse<List<Analysis>>> data = new MutableLiveData<>();
        try{
            this.analysisRequest.month(uid).enqueue(new Callback<CommonResponse<List<Analysis>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<Analysis>>> call, Response<CommonResponse<List<Analysis>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<Analysis>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<List<Analysis>>> week(Integer uid){
        MutableLiveData<CommonResponse<List<Analysis>>> data = new MutableLiveData<>();
        try{
            this.analysisRequest.week(uid).enqueue(new Callback<CommonResponse<List<Analysis>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<Analysis>>> call, Response<CommonResponse<List<Analysis>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<Analysis>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
