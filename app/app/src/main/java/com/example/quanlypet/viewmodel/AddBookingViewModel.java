package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.model.Service;
import com.example.quanlypet.model.User;
import com.example.quanlypet.repository.AnimalRepository;
import com.example.quanlypet.repository.BookRepository;
import com.example.quanlypet.repository.ServiceRepository;
import com.example.quanlypet.repository.UserRepository;

import java.util.List;

public class AddBookingViewModel extends AndroidViewModel {

    private AnimalRepository animalRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private ServiceRepository serviceRepository;

    public AddBookingViewModel(@NonNull Application application) {
        super(application);
        this.animalRepository = new AnimalRepository();
        this.userRepository = new UserRepository();
        this.bookRepository = new BookRepository();
        this.serviceRepository = new ServiceRepository();
    }

    public LiveData<CommonResponse<List<User>>> listUser(Integer uid, Integer idRole){
        return this.userRepository.list(uid, idRole);
    }

    public LiveData<CommonResponse<List<Animal>>> listAnimal(Integer uid) {
        return this.animalRepository.list(uid);
    }

    public LiveData<CommonResponse<List<Service>>> listService(Integer uid) {
        return this.serviceRepository.list(uid);
    }

    public LiveData<CommonResponse<Book>> save(Book book){
        return this.bookRepository.save(book);
    }
}
