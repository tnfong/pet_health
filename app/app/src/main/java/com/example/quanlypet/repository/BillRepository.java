package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.Bill;
import com.example.quanlypet.retrofit.BillRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillRepository {
    private final String TAG = this.getClass().getName();
    private final BillRequest billRequest;

    public BillRepository(){
        this.billRequest = RetrofitRequest.instance().create(BillRequest.class);
    }

    public LiveData<CommonResponse<List<Bill>>> list(Integer uid){
        MutableLiveData<CommonResponse<List<Bill>>> data = new MutableLiveData<>();
        try{
            this.billRequest.list(uid).enqueue(new Callback<CommonResponse<List<Bill>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<Bill>>> call, Response<CommonResponse<List<Bill>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<Bill>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<Bill>> init(Integer uid, Integer idBook){
        MutableLiveData<CommonResponse<Bill>> data = new MutableLiveData<>();
        try{
            this.billRequest.init(uid, idBook).enqueue(new Callback<CommonResponse<Bill>>() {
                @Override
                public void onResponse(Call<CommonResponse<Bill>> call, Response<CommonResponse<Bill>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Bill>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<Bill>> save(Integer uid, Bill bill){
        MutableLiveData<CommonResponse<Bill>> data = new MutableLiveData<>();
        try{
            this.billRequest.save(uid, bill).enqueue(new Callback<CommonResponse<Bill>>() {
                @Override
                public void onResponse(Call<CommonResponse<Bill>> call, Response<CommonResponse<Bill>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Bill>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
