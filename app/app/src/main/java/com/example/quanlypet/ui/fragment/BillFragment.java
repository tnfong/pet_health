package com.example.quanlypet.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.adapter.viewpager2.bill.BillAdapter;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Bill;
import java.util.ArrayList;
import java.util.List;

public class BillFragment extends Fragment {
    private MainActivity activity;
    private RecyclerView rcvBill;
    private List<Bill> bills = new ArrayList<>();
    private BillAdapter adapterBill;

    public BillFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectView(view);
        loadDataBill();
    }

    private void connectView(View view){
        rcvBill = view.findViewById(R.id.rcv_bill);
        adapterBill = new BillAdapter(activity, bills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvBill.setLayoutManager(layoutManager);
        rcvBill.setAdapter(adapterBill);
    }

    private void loadDataBill(){
        Integer uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<Bill>>> lvData = this.activity.getMainViewModel().billList(uid);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                bills.clear();
                bills.addAll(data.getData());
                bills.forEach(Bill::decrypt);
                adapterBill.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}