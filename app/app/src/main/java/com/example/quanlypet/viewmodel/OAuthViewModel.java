package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.User;
import com.example.quanlypet.repository.OAuthRepository;
import com.example.quanlypet.repository.UserRepository;

public class OAuthViewModel extends AndroidViewModel {

    private OAuthRepository oauthRepository;
    private UserRepository userRepository;

    public OAuthViewModel(@NonNull Application application) {
        super(application);
        this.oauthRepository = new OAuthRepository();
        this.userRepository = new UserRepository();
    }

    public LiveData<CommonResponse<String>> login(User user){
        return this.oauthRepository.login(user);
    }

    public LiveData<CommonResponse<User>> register(User user){
        return this.oauthRepository.register(user);
    }

    public LiveData<CommonResponse<User>> userInfo(Integer uid){
        return this.userRepository.getInfo(uid);
    }
}