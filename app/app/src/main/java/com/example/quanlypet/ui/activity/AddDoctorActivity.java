package com.example.quanlypet.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.FileUtils;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.common.utils.PermissionUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.common.utils.ValidationUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.AddDoctorViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddDoctorActivity extends AppCompatActivity{
    private AddDoctorViewModel addDoctorViewModel;
    private TextInputEditText edUsername, edNameDocter, edPhoneDocter;
    private RadioButton rdoBoy, rdoGirl;
    private TextInputEditText edEmailDocter, edSpecializeDocter;
    private Button btnAddDocter, btnDelete;
    private int checkGender;
    private Toolbar toolbar;
    private CircleImageView imgPicture;
    private ImageView btnAlbum;
    int REQUEST_CODE_ALBUM = 123;
    private User doctor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_docter);

        connectView();
        initData();
        actionView();
    }

    private void initData(){
        Integer id = getIntent().getIntExtra(DataStatic.INTENT.ID, 0);
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        if(id.equals(0)){
            doctor = new User(0);
            btnDelete.setVisibility(View.GONE);
        }else{
            LiveData<CommonResponse<User>> lvData = addDoctorViewModel.getById(uid, id);
            lvData.observe(this, data -> {
                if(data.getStatus()){
                    doctor = data.getData();
                    doctor.decrypt();
                    showDoctorData();
                }
            });
        }
    }

    private void showDoctorData(){
        edNameDocter.setText(doctor.getFullName());
        edPhoneDocter.setText(doctor.getPhone());
        edEmailDocter.setText(doctor.getEmail());
        edSpecializeDocter.setText(doctor.getSpecialize());
        ImageUtils.loadUrl(this, imgPicture, doctor.getAvatarUrl());
        if(doctor.getGender() == 1){
            rdoBoy.setActivated(true);
            rdoBoy.setChecked(true);
        }else{
            rdoGirl.setActivated(true);
            rdoGirl.setChecked(true);
        }
    }

    private void saveDoctor(){
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<User>> lvData = addDoctorViewModel.save(uid, doctor);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                finish();
            }
        });
    }

    private void connectView(){
        addDoctorViewModel = ViewModelProviders.of(this).get(AddDoctorViewModel.class);
        edUsername = findViewById(R.id.ed_username);
        edNameDocter = findViewById(R.id.ed_nameDocter);
        edPhoneDocter = findViewById(R.id.ed_phoneDocter);
        rdoBoy = findViewById(R.id.rdo_boy);
        rdoGirl = findViewById(R.id.rdo_girl);
        edEmailDocter = findViewById(R.id.ed_emailDocter);
        edSpecializeDocter = findViewById(R.id.ed_specializeDocter);
        btnAddDocter = findViewById(R.id.btn_addDocter);
        imgPicture = findViewById(R.id.img_picture);
        btnAlbum = findViewById(R.id.btn_album);
        btnDelete = findViewById(R.id.btn_delete);
        toolbar = findViewById(R.id.id_tollBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm Bác sĩ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void actionView(){
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PermissionUtils.readExternalStorage(AddDoctorActivity.this)){
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType("image/*");
                    startActivityForResult(i, REQUEST_CODE_ALBUM);
                }
            }
        });

        btnAddDocter.setOnClickListener(v-> {
            String username = edUsername.getText().toString().trim();
            String name = edNameDocter.getText().toString().trim();
            String phone = edPhoneDocter.getText().toString().trim();

            String email = edEmailDocter.getText().toString().trim();
            String specialize = edSpecializeDocter.getText().toString().trim();
            checkGender = rdoBoy.isChecked() ? 1 : 0;

            if (username.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty() || specialize.isEmpty()) {
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            } else if(!ValidationUtils.isPhone(phone)){
                Toast.makeText(this, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
            } else if(!ValidationUtils.isMail(email)) {
                Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            } else{
                doctor.setFullName(name);
                doctor.setPhone(phone);
                doctor.setEmail(email);
                doctor.setSpecialize(specialize);
                doctor.setGender(checkGender);
                doctor.setIdRole(2);
                doctor.encrypt();
                saveDoctor();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer uid = SessionUtils.get(AddDoctorActivity.this, DataStatic.SESSION.KEY.USER_ID, 0);
                LiveData<CommonResponse<Boolean>> lvData = addDoctorViewModel.delete(uid, doctor.getId());
                lvData.observe(AddDoctorActivity.this, data -> {
                    if(data.getStatus()){
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ALBUM && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            doctor.setAvatarFileUri(FileUtils.getPath(AddDoctorActivity.this, uri));
            ImageUtils.loadUri(this, imgPicture, uri);
        }
    }
}