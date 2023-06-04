package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.Guest;
import com.example.quanlypet.model.Service;
import com.example.quanlypet.retrofit.GuestRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;
import com.example.quanlypet.retrofit.ServiceRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceRepository {
    private final String TAG = this.getClass().getName();
    private final ServiceRequest serviceRequest;

    public ServiceRepository(){
        this.serviceRequest = RetrofitRequest.instance().create(ServiceRequest.class);
    }

    public LiveData<CommonResponse<List<Service>>> list(Integer uid){
        MutableLiveData<CommonResponse<List<Service>>> data = new MutableLiveData<>();
        try{
            this.serviceRequest.list(uid).enqueue(new Callback<CommonResponse<List<Service>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<Service>>> call, Response<CommonResponse<List<Service>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<Service>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
