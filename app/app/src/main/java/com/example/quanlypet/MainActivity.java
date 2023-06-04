package com.example.quanlypet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.quanlypet.adapter.viewpager2.ViewPager2Adapter;

import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.effect.DepthPageTransformer;
import com.example.quanlypet.ui.activity.PatientActivity;
import com.example.quanlypet.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();
        actionView();
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    private void connectView(){
        this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewPager2 = findViewById(R.id.view_pager2);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        toolbar = findViewById(R.id.id_tollBar);
        setSupportActionBar(toolbar);
        bottomNavigationView.setItemIconTintList(null);
        ViewPager2Adapter adapter = new ViewPager2Adapter(this);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);
        toolbar.setNavigationIcon(R.drawable.home_item);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        if(UserUtils.isAdmin()){
            bottomNavigationView.getMenu().findItem(R.id.docter).setVisible(true);
        }
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    private void actionView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        viewPager2.setCurrentItem(0);
                        toolbar.setTitle("Home");
                        toolbar.setTitleTextColor(Color.WHITE);
                        toolbar.setNavigationIcon(R.drawable.home_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                    case R.id.docter:
                        viewPager2.setCurrentItem(1);
                        toolbar.setTitle("Bác Sĩ");
                        toolbar.setTitleTextColor(Color.WHITE);
                        toolbar.setNavigationIcon(R.drawable.doctor_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                    case R.id.book:
                        viewPager2.setCurrentItem(2);
                        toolbar.setTitle("Đặt Lịch");
                        toolbar.setTitleTextColor(Color.WHITE);
                        toolbar.setNavigationIcon(R.drawable.booking_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                    case R.id.bill:
                        viewPager2.setCurrentItem(3);
                        toolbar.setTitle("Hóa Đơn");
                        toolbar.setTitleTextColor(Color.WHITE);
                        toolbar.setNavigationIcon(R.drawable.bill_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                    case R.id.account:
                        viewPager2.setCurrentItem(4);
                        toolbar.setTitle("Tài Khoản");
                        toolbar.setTitleTextColor(Color.WHITE);
                        toolbar.setNavigationIcon(R.drawable.account_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                Log.i("QRCODE: ", intentResult.getContents());
                Intent intent = new Intent(this, PatientActivity.class);
                intent.putExtra(DataStatic.INTENT.ID_ANIMAL, Integer.parseInt(intentResult.getContents()));
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


