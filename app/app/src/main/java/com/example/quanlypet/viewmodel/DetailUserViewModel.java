package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.model.User;
import com.example.quanlypet.repository.UserRepository;

public class DetailUserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public DetailUserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository();
    }

    public LiveData<CommonResponse<User>> info(Integer uid){
        return this.userRepository.getInfo(uid);
    }

    public LiveData<CommonResponse<User>> save(Integer uid, User user){
        return this.userRepository.save(uid, user);
    }

    public LiveData<CommonResponse<Boolean>> delete(Integer uid, Integer id){
        return this.userRepository.delete(uid, id);
    }
}
