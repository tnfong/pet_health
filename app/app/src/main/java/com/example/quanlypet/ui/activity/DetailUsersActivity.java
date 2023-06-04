package com.example.quanlypet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.common.utils.ValidationUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.DetailUserViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class DetailUsersActivity extends AppCompatActivity {
    private Toolbar idTollBar;
    private DetailUserViewModel detailUserViewModel;
    private TextInputEditText etUsername, etPassword, etFullName, etMail, etPhone;
    private RadioButton rdoBoy, rdoGirl;
    private Button btnUpdate, btnDelete;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_users);

        connectView();
        actionView();
        loadData();

    }

    private boolean updateUser() {
        String password = etPassword.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String email = etMail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        Integer gender = rdoBoy.isChecked() ? 1 : 0;
        if(fullName.length() == 0){
            Toast.makeText(this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!ValidationUtils.isMail(email)){
            Toast.makeText(this, "Mail sai định dạng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!ValidationUtils.isPhone(phone)){
            Toast.makeText(this, "Số điện thoại sai định dạng", Toast.LENGTH_SHORT).show();
            return false;
        }
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setGender(gender);
        user.encrypt();
        LiveData<CommonResponse<User>> lvData = detailUserViewModel.save(user.getId(), user);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                Toast.makeText(this, "Cập nhập thông tin người dùng thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        return true;
    }

    public void connectView() {
        detailUserViewModel = ViewModelProviders.of(this).get(DetailUserViewModel.class);
        idTollBar = findViewById(R.id.id_tollBar);
        etUsername = findViewById(R.id.TIED_ImportName);
        etPassword = findViewById(R.id.TIED_password);
        etFullName = findViewById(R.id.TIED_NameUser);
        etMail = findViewById(R.id.TIED_Email);
        etPhone = findViewById(R.id.TIED_PhoneNumber);

        rdoBoy = findViewById(R.id.rdo_boy);
        rdoGirl = findViewById(R.id.rdo_girl);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        setSupportActionBar(idTollBar);
        getSupportActionBar().setTitle("Thông tin người dùng");
        idTollBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUsername.setEnabled(false);
    }

    private void loadData(){
        Integer id = getIntent().getIntExtra(DataStatic.INTENT.ID, 0);
        LiveData<CommonResponse<User>> lvData = detailUserViewModel.info(id);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                user = data.getData();
                user.decrypt();
                showData();
            }
        });
    }

    private void showData(){
        etUsername.setText(user.getUsername());
        etFullName.setText(user.getFullName());
        etMail.setText(user.getEmail());
        etPhone.setText(user.getPhone());
        if(user.getGender() == 1){
            rdoBoy.setActivated(true);
            rdoBoy.setChecked(true);
        }else{
            rdoGirl.setActivated(true);
            rdoGirl.setChecked(true);
        }

    }

    private void actionView(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer uid = SessionUtils.get(DetailUsersActivity.this, DataStatic.SESSION.KEY.USER_ID, 0);
                LiveData<CommonResponse<Boolean>> lvData = detailUserViewModel.delete(uid, user.getId());
                lvData.observe(DetailUsersActivity.this, data -> {
                    if(data.getStatus()){
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}