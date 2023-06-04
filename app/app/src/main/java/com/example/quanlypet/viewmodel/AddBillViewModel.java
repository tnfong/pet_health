package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Bill;
import com.example.quanlypet.repository.BillRepository;

public class AddBillViewModel extends AndroidViewModel {

    private BillRepository billRepository;

    public AddBillViewModel(@NonNull Application application) {
        super(application);
        this.billRepository = new BillRepository();
    }

    public LiveData<CommonResponse<Bill>> init(Integer uid, Integer idBook){
        return this.billRepository.init(uid, idBook);
    }

    public LiveData<CommonResponse<Bill>> save(Integer uid, Bill bill){
        return this.billRepository.save(uid, bill);
    }
}
