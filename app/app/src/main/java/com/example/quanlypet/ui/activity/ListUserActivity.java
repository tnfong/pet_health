package com.example.quanlypet.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.adUse.ListUserAdapter;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.ListUserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {
    private static final int MY_REQUESTCODE = 111;
    RecyclerView recyclerView;
    ListUserAdapter adapter;
    private SearchView searchView;
    List<User> list;
    private ListUserViewModel listUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        connectView();
    }

    private void connectView(){
        this.listUserViewModel = ViewModelProviders.of(this).get(ListUserViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh Sách Người Dùng");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recy_list_user);
        list = new ArrayList<>();
        adapter = new ListUserAdapter(this, list);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_users, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.seach_user).getActionView();
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

    public void loadData() {
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<User>>> lvData = listUserViewModel.userList(uid);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                list.clear();
                list.addAll(data.getData());
                list.forEach(User::decrypt);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}