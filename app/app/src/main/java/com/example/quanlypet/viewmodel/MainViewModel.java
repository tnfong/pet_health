package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Analysis;
import com.example.quanlypet.model.Bill;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.model.Config;
import com.example.quanlypet.model.User;
import com.example.quanlypet.repository.AnalysisRepository;
import com.example.quanlypet.repository.BillRepository;
import com.example.quanlypet.repository.BookRepository;
import com.example.quanlypet.repository.ConfigRepository;
import com.example.quanlypet.repository.UserRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private AnalysisRepository analysisRepository;
    private BillRepository billRepository;
    private ConfigRepository configRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository();
        this.bookRepository = new BookRepository();
        this.analysisRepository = new AnalysisRepository();
        this.billRepository = new BillRepository();
        this.configRepository = new ConfigRepository();
    }

    public LiveData<CommonResponse<Config>> configGet(Integer uid, String key){
        return this.configRepository.get(uid, key);
    }

    public LiveData<CommonResponse<List<User>>> doctorList(Integer uid){
        return this.userRepository.list(uid, 2);
    }

    public LiveData<CommonResponse<List<Book>>> bookList(Integer uid, Integer idStatus){
        return this.bookRepository.list(uid, idStatus);
    }

    public LiveData<CommonResponse<List<Bill>>> billList(Integer uid){
        return this.billRepository.list(uid);
    }

    public LiveData<CommonResponse<List<Analysis>>> analysisMonth(Integer uid){
        return this.analysisRepository.month(uid);
    }

    public LiveData<CommonResponse<List<Analysis>>> analysisWeek(Integer uid){
        return this.analysisRepository.week(uid);
    }

    public LiveData<CommonResponse<Bill>> billSave(Integer uid, Bill bill){
        return this.billRepository.save(uid, bill);
    }

}
