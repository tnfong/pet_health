package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.User;
import com.example.quanlypet.repository.UserRepository;

import java.util.List;

public class ListDoctorViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public ListDoctorViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository();
    }

    public LiveData<CommonResponse<List<User>>> doctorList(Integer uid){
        return this.userRepository.list(uid, 2);
    }
}
