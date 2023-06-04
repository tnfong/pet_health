package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.retrofit.AuthRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;
import com.example.quanlypet.retrofit.UserRequest;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRepository {
    private final String TAG = this.getClass().getName();
    private final UserRequest userRequest;

    public UserRepository(){
        this.userRequest = RetrofitRequest.instance().create(UserRequest.class);
    }

    public LiveData<CommonResponse<List<User>>> list(Integer uid, Integer idRole){
        MutableLiveData<CommonResponse<List<User>>> data = new MutableLiveData<>();
        try{
            this.userRequest.list(uid, idRole).enqueue(new Callback<CommonResponse<List<User>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<User>>> call, Response<CommonResponse<List<User>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<User>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<User>> getById(Integer uid, Integer id){
        MutableLiveData<CommonResponse<User>> data = new MutableLiveData<>();
        try{
            this.userRequest.getById(uid, id).enqueue(new Callback<CommonResponse<User>>() {
                @Override
                public void onResponse(Call<CommonResponse<User>> call, Response<CommonResponse<User>> response) {
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
    }

    public LiveData<CommonResponse<User>> getInfo(Integer uid){
        MutableLiveData<CommonResponse<User>> data = new MutableLiveData<>();
        try{
            this.userRequest.info(uid).enqueue(new Callback<CommonResponse<User>>() {
                @Override
                public void onResponse(Call<CommonResponse<User>> call, Response<CommonResponse<User>> response) {
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
    }

    public LiveData<CommonResponse<User>> changePass(Integer uid, String password){
        MutableLiveData<CommonResponse<User>> data = new MutableLiveData<>();
        try{
            this.userRequest.changePass(uid, password).enqueue(new Callback<CommonResponse<User>>() {
                @Override
                public void onResponse(Call<CommonResponse<User>> call, Response<CommonResponse<User>> response) {
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
    }

    public LiveData<CommonResponse<User>> save(Integer uid, User user){
        MutableLiveData<CommonResponse<User>> data = new MutableLiveData<>();
        try{
            this.userRequest.save(uid, user.getBodyUsername(), user.getBodyPassword(), user.getBodyFullName()
                    , user.getBodyEmail(), user.getBodyPhone(), user.getBodyGender(), user.getBodyIdRole()
                    , user.getBodyAvatarUrl(), user.getBodySpecialize(), user.getBodyAvatarFile()).enqueue(new Callback<CommonResponse<User>>() {
                @Override
                public void onResponse(Call<CommonResponse<User>> call, Response<CommonResponse<User>> response) {
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
    }

    public LiveData<CommonResponse<Boolean>> delete(Integer uid, Integer id){
        MutableLiveData<CommonResponse<Boolean>> data = new MutableLiveData<>();
        try{
            this.userRequest.delete(uid, id).enqueue(new Callback<CommonResponse<Boolean>>() {
                @Override
                public void onResponse(Call<CommonResponse<Boolean>> call, Response<CommonResponse<Boolean>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Boolean>> call, Throwable t) {
                    Log.e("UserRepository", "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
