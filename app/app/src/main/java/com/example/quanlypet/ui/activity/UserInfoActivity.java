package com.example.quanlypet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.common.utils.ValidationUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.UserInfoViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UserInfoActivity extends AppCompatActivity {
    private UserInfoViewModel userInfoViewModel;
    private Toolbar idTollBar;
    private TextInputEditText edUsername, edFullName, edEmailUsers, edPhoneUsers;
    private RadioButton rdoBoy, rdoGirl;
    private Button btnUpdateUser;
    private TextView tvThongbao;
    private Button btnCancelUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_users);

        connectView();
        actionView();
        loadUserInfo();

    }

    private void connectView(){

        userInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        idTollBar = findViewById(R.id.id_tollBar);
        edUsername = findViewById(R.id.ed_username);
        edFullName = findViewById(R.id.ed_fullnameUser);
        edEmailUsers = findViewById(R.id.ed_emailUsers);
        edPhoneUsers = findViewById(R.id.ed_phoneUsers);
        btnUpdateUser = findViewById(R.id.btn_updateUser);
        tvThongbao = findViewById(R.id.tv_thongbao);
        btnCancelUser = findViewById(R.id.btn_cancelUser);
        rdoBoy = findViewById(R.id.rdo_boy);
        rdoGirl = findViewById(R.id.rdo_girl);
        tvThongbao.setVisibility(View.INVISIBLE);

        setSupportActionBar(idTollBar);
        getSupportActionBar().setTitle("Personal Information");
        idTollBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void actionView(){
        btnUpdateUser.setOnClickListener(v->{
            String fullname = edFullName.getText().toString().trim();
            String email = edEmailUsers.getText().toString().trim();
            String phone = edPhoneUsers.getText().toString().trim();
            Integer gender = rdoBoy.isChecked() ? 1 : 0;

            if (fullname.isEmpty()||email.isEmpty()||phone.isEmpty()){
                tvThongbao.setVisibility(View.VISIBLE);
            }else if(!ValidationUtils.isPhone(phone)) {
                Toast.makeText(this, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
            } else if(!ValidationUtils.isMail(email)) {
                Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            } else {
                user.setFullName(fullname);
                user.setEmail(email);
                user.setPhone(phone);
                user.setGender(gender);
                user.encrypt();
                Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
                LiveData<CommonResponse<User>> lvData = userInfoViewModel.save(uid, user);
                lvData.observe(UserInfoActivity.this, data -> {
                    if(data.getStatus()){
                        tvThongbao.setText("Update successfully");
                        user = data.getData();
                        finish();
                    }
                });
            }
        });
        btnCancelUser.setOnClickListener(view -> {
            finish();
        });
    }

    private void loadUserInfo(){
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<User>> lvData = userInfoViewModel.info(uid);
        lvData.observe(UserInfoActivity.this, data -> {
            if(data.getStatus()){
                user = data.getData();
                user.decrypt();
                showUserInfo();
            }
        });
    }

    private void showUserInfo(){
        edUsername.setText(user.getUsername());
        edFullName.setText(user.getFullName());
        edPhoneUsers.setText(user.getPhone());
        edEmailUsers.setText(user.getEmail());
        if(user.getGender() == 1){
            rdoBoy.setActivated(true);
            rdoBoy.setChecked(true);
        }else{
            rdoGirl.setActivated(true);
            rdoGirl.setChecked(true);
        }
    }
}