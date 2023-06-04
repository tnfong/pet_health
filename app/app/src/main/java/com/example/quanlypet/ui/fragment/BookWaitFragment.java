package com.example.quanlypet.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.adapter.booking.BookingAdapter;
import com.example.quanlypet.adapter.booking.BookingAdminAdapter;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookWaitFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    private MainViewModel mainViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<Book> list;
    BookingAdapter adapter;
    BookingAdminAdapter adapterAdmin;
    RecyclerView rvBooking;
    MainActivity activity;

    public BookWaitFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_wait, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectView(view);
        loadDataBook();
    }

    private void connectView(View view){
        mainViewModel = ViewModelProviders.of(activity).get(MainViewModel.class);
        rvBooking = view.findViewById(R.id.rv_data);
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rvBooking.setLayoutManager(linearLayoutManager);
        if (UserUtils.isAdmin()) {
            adapterAdmin = new BookingAdminAdapter(list, activity);
            rvBooking.setAdapter(adapterAdmin);
        } else {
            adapter = new BookingAdapter(activity);
            rvBooking.setAdapter(adapter);
        }
    }

    private void loadDataBook(){
        Integer uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<Book>>> lvData = this.activity.getMainViewModel().bookList(uid, 3);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                if (UserUtils.isAdmin()) {
                    adapterAdmin.setDATA(data.getData());
                } else {
                    adapter.setDATA(data.getData());
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        loadDataBook();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataBook();
    }
}