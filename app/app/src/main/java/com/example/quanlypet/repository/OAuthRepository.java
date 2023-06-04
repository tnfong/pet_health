package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.retrofit.AuthRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OAuthRepository {
    private final String TAG = this.getClass().getName();
    private final AuthRequest authRequest;

    public OAuthRepository(){
        this.authRequest = RetrofitRequest.instance().create(AuthRequest.class);
    }
    
    public LiveData<CommonResponse<String>> login(User user){
        MutableLiveData<CommonResponse<String>> data = new MutableLiveData<>();
        try{
            this.authRequest.login(user).enqueue(new Callback<CommonResponse<String>>() {
                @Override
                public void onResponse(Call<CommonResponse<String>> call, Response<CommonResponse<String>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<String>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<User>> register(User user){
        MutableLiveData<CommonResponse<User>> data = new MutableLiveData<>();
        this.authRequest.register(user.getBodyUsername(), user.getBodyPassword(), user.getBodyFullName(), user.getBodyPhone(), user.getBodyEmail(), user.getBodyGender()).enqueue(new Callback<CommonResponse<User>>() {
            @Override
            public void onResponse(Call<CommonResponse<User>> call, Response<CommonResponse<User>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<CommonResponse<User>> call, Throwable t) {

            }
        });
        return data;
    }
}
