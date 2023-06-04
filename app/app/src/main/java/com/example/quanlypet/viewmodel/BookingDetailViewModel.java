package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.repository.BookRepository;

public class BookingDetailViewModel extends AndroidViewModel {

    private BookRepository bookRepository;

    public BookingDetailViewModel(@NonNull Application application) {
        super(application);
        this.bookRepository = new BookRepository();
    }

    public LiveData<CommonResponse<Book>> info(Integer uid, Integer id){
        return this.bookRepository.info(uid, id);
    }
}
