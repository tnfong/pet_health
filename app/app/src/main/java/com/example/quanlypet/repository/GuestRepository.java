package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.Guest;
import com.example.quanlypet.retrofit.GuestRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GuestRepository {
    private final String TAG = this.getClass().getName();
    private final GuestRequest guestRequest;

    public GuestRepository(){
        this.guestRequest = RetrofitRequest.instance().create(GuestRequest.class);
    }

    public LiveData<CommonResponse<List<Guest>>> list(Integer uid, Integer idAnimal){
        MutableLiveData<CommonResponse<List<Guest>>> data = new MutableLiveData<>();
        try{
            this.guestRequest.list(uid, idAnimal).enqueue(new Callback<CommonResponse<List<Guest>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<Guest>>> call, Response<CommonResponse<List<Guest>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<Guest>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
/*
    public LiveData<CommonResponse<Book>> getById(Integer uid, Integer id){
        MutableLiveData<CommonResponse<Book>> data = new MutableLiveData<>();
        try{
            this.bookRequest.getById(uid, id).enqueue(new Callback<CommonResponse<Book>>() {
                @Override
                public void onResponse(Call<CommonResponse<Book>> call, Response<CommonResponse<Book>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<User>> call, Throwable t) {
                    Log.e("UserRepository", "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }*/

    public LiveData<CommonResponse<Guest>> save(Integer uid, Guest guest){
        MutableLiveData<CommonResponse<Guest>> data = new MutableLiveData<>();
        try{
            this.guestRequest.save(uid, guest)
                    .enqueue(new Callback<CommonResponse<Guest>>() {
                @Override
                public void onResponse(Call<CommonResponse<Guest>> call, Response<CommonResponse<Guest>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Guest>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
