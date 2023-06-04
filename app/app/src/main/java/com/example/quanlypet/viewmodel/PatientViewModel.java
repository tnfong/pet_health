package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Guest;
import com.example.quanlypet.repository.GuestRepository;

import java.util.List;

public class PatientViewModel extends AndroidViewModel {

    private GuestRepository guestRepository;

    public PatientViewModel(@NonNull Application application) {
        super(application);
        this.guestRepository = new GuestRepository();
    }

    public LiveData<CommonResponse<List<Guest>>> list(Integer uid, Integer idAnimal){
        return this.guestRepository.list(uid, idAnimal);
    }
}
