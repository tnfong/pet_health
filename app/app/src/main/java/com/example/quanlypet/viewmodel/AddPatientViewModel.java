package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.model.Guest;
import com.example.quanlypet.repository.BookRepository;
import com.example.quanlypet.repository.GuestRepository;

public class AddPatientViewModel extends AndroidViewModel {

    private GuestRepository guestRepository;

    public AddPatientViewModel(@NonNull Application application) {
        super(application);
        this.guestRepository = new GuestRepository();
    }

    public LiveData<CommonResponse<Guest>> save(Integer uid, Guest guest){
        return this.guestRepository.save(uid, guest);
    }
}
