package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.repository.AnimalRepository;

import java.util.List;

public class AnimalViewModel extends AndroidViewModel {

    private AnimalRepository animalRepository;

    public AnimalViewModel(@NonNull Application application) {
        super(application);
        this.animalRepository = new AnimalRepository();
    }

    public LiveData<CommonResponse<List<Animal>>> list(Integer uid){
        return this.animalRepository.list(uid);
    }

    public LiveData<CommonResponse<Animal>> save(Animal animal){
        return this.animalRepository.save(animal);
    }
}
