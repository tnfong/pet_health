package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.User;
import com.example.quanlypet.repository.UserRepository;

public class DoctorInfoViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public DoctorInfoViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository();
    }

    public LiveData<CommonResponse<User>> getById(Integer uid, Integer id){
        return this.userRepository.getById(uid, id);
    }
}
