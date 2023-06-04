package com.example.quanlypet.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.common.utils.PermissionUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.ui.welcome.LoginActivity;
import com.example.quanlypet.viewmodel.AddAnimalViewModel;
import com.example.quanlypet.viewmodel.DoctorInfoViewModel;


public class DoctorInfoActivity extends AppCompatActivity {
    private DoctorInfoViewModel doctorInfoViewModel;
    private TextView tvSpecialize;
    private TextView tvName;
    private TextView tvPhone;
    private Toolbar toolbar;
    private ImageView imgDt;
    private Button btnCall;
    private CardView cardView;
    private Button btnCancel;
    private User user = new User();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_infor);

        connectView();
        actionView();
        loadInfo();

    }

    private void connectView(){
        doctorInfoViewModel = ViewModelProviders.of(this).get(DoctorInfoViewModel.class);
        tvSpecialize = findViewById(R.id.tv_specializeDocter);
        tvName = findViewById(R.id.tv_nameDocter);
        imgDt = findViewById(R.id.img_dt);
        tvPhone = findViewById(R.id.tv_phoneDocter);
        cardView = findViewById(R.id.card_callPhone);
        toolbar = findViewById(R.id.id_tollBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông tin chi tiết");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void actionView(){
        cardView.setOnClickListener(v->{
            cardCallPhone();
        });
    }

    private void showInfo(){
        tvName.setText(user.getFullName());
        tvPhone.setText(user.getPhone());
        tvSpecialize.setText(user.getSpecialize());
        ImageUtils.loadUrl(this, imgDt, user.getAvatarUrl());
    }

    private void loadInfo(){
        Integer id = getIntent().getIntExtra(DataStatic.INTENT.ID, 0);
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<User>> lvData = doctorInfoViewModel.getById(uid, id);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                user = data.getData();
                user.decrypt();
                showInfo();
            }
        });
    }

    public void cardCallPhone(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_call_phone);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog_call));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;
        btnCall = dialog.findViewById(R.id.btn_call);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnCall.setText(tvPhone.getText());
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PermissionUtils.callPhone(DoctorInfoActivity.this)){
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " +tvPhone.getText()));
                    startActivity(intent);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}