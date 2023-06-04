package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.User;
import com.example.quanlypet.retrofit.AnimalRequest;
import com.example.quanlypet.retrofit.AuthRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnimalRepository {
    private final String TAG = this.getClass().getName();
    private final AnimalRequest animalRequest;
    public AnimalRepository(){
        this.animalRequest = RetrofitRequest.instance().create(AnimalRequest.class);
    }

    public LiveData<CommonResponse<List<Animal>>> list(Integer uid){
        MutableLiveData<CommonResponse<List<Animal>>> data = new MutableLiveData<>();
        try{
            this.animalRequest.list(uid).enqueue(new Callback<CommonResponse<List<Animal>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<Animal>>> call, Response<CommonResponse<List<Animal>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<Animal>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<Animal>> getById(Integer uid, Integer id){
        MutableLiveData<CommonResponse<Animal>> data = new MutableLiveData<>();
        try{
            this.animalRequest.getById(uid, id).enqueue(new Callback<CommonResponse<Animal>>() {
                @Override
                public void onResponse(Call<CommonResponse<Animal>> call, Response<CommonResponse<Animal>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Animal>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<Animal>> save(Animal animal){

        MultipartBody.Part multipart = null;
        RequestBody requestFile = null;

        if(animal.getAvatarUri() != null && animal.getAvatarUri().length() > 0){
            File file = new File(animal.getAvatarUri());
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            multipart = MultipartBody.Part.createFormData("avatar_file", file.getName(), requestFile);
        }else{
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            multipart = MultipartBody.Part.createFormData("avatar_file", "", requestFile);
        }

        MutableLiveData<CommonResponse<Animal>> data = new MutableLiveData<>();
        try{
            this.animalRequest.save(animal.getIdUser(), animal.getBodyId(), animal.getBodyAvatarUrl()
                    , animal.getBodyName(), animal.getBodyAge(), animal.getBodySpecies(), animal.getBodyIdStatus(), animal.getBodyDescription(), multipart).enqueue(new Callback<CommonResponse<Animal>>() {
                @Override
                public void onResponse(Call<CommonResponse<Animal>> call, Response<CommonResponse<Animal>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Animal>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
