package com.example.quanlypet.ui.activity;

import android.Manifest;
import android.app.SearchManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.doctor.DoctorAdapter;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.ListDoctorViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListDoctorActivity extends AppCompatActivity {
    private ListDoctorViewModel listDoctorViewModel;
    private RecyclerView rcvDoctor;
    private ArrayList<User> list = new ArrayList<>();
    private ArrayList<User> list1 = new ArrayList<>();
    private Toolbar idTollBar;
    private DoctorAdapter adapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);

        checkPermission();
        connectView();
        loadDataDoctor();
        getDS();
    }

    private void connectView(){
        listDoctorViewModel = ViewModelProviders.of(this).get(ListDoctorViewModel.class);
        rcvDoctor = (RecyclerView) findViewById(R.id.rcv_Doctor);
        idTollBar = (Toolbar) findViewById(R.id.id_tollBar);
        setSupportActionBar(idTollBar);
        getSupportActionBar().setTitle("Thông tin bác sĩ");
        adapter = new DoctorAdapter(this);
        adapter.setDataDocter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rcvDoctor.setLayoutManager(layoutManager);
        rcvDoctor.setAdapter(adapter);
    }

    private void loadDataDoctor(){
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<User>>> lvData = listDoctorViewModel.doctorList(uid);
        lvData.observe(ListDoctorActivity.this, data -> {
            if(data.getStatus()){
                adapter.setDataDocter(data.getData());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_huy_docter, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.error).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void getDS() {
        list1.add(new User("Hệ thống hỗ trợ", R.drawable.doctor, "0384972984"));
    }
    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT > 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.CALL_PHONE
                }, 1);
                return false;
            }
        } else {
            return true;
        }
    }
}