package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.User;
import com.example.quanlypet.repository.OAuthRepository;
import com.example.quanlypet.repository.UserRepository;

public class ChangePasswordViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository();
    }

    public LiveData<CommonResponse<User>> changePass(Integer uid, String password){
        return this.userRepository.changePass(uid, password);
    }
}
