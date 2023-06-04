package com.example.quanlypet.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.CryptoUtils;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.viewmodel.BookingDetailViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BookingDetailActivity extends AppCompatActivity {

    private TextInputEditText tietDoctorName, tietDoctorPhone, tietCustomerName, tietCustomerPhone
    , tietNamePet, tietSpeciesPet, tietDescription, tietTime, tietTimeHold, tietService;
    private CircleImageView ivPicture;
    private BookingDetailViewModel bookingDetailViewModel;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        connectView();
        initData();
    }

    private void initData(){
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        Integer id = getIntent().getIntExtra(DataStatic.INTENT.ID, 0);
        LiveData<CommonResponse<Book>> lvData = bookingDetailViewModel.info(uid, id);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                showData(data.getData());
            }
        });
    }

    private void showData(Book book){
        try {
            this.tietDoctorName.setText(CryptoUtils.AES.decrypt(book.getDoctorFullName()));
            this.tietDoctorPhone.setText(CryptoUtils.AES.decrypt(book.getDoctorPhone()));
            this.tietCustomerName.setText(CryptoUtils.AES.decrypt(book.getUserFullName()));
            this.tietCustomerPhone.setText(CryptoUtils.AES.decrypt(book.getUserPhone()));
            this.tietNamePet.setText(book.getAnimalName());
            this.tietSpeciesPet.setText(book.getAnimalSpecies());
            this.tietDescription.setText(book.getDescription());
            this.tietTime.setText(sdf.format(book.getTime()));
            this.tietTimeHold.setText(sdf.format(book.getTimeHold()));
            this.tietService.setText(book.getServiceName());
            ImageUtils.loadUrl(this, this.ivPicture, book.getPhotoUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void connectView(){
        this.tietDoctorName = findViewById(R.id.tiet_doctor_name);
        this.tietDoctorPhone = findViewById(R.id.tiet_doctor_phone);
        this.tietCustomerName = findViewById(R.id.tiet_customer_name);
        this.tietCustomerPhone = findViewById(R.id.tiet_customer_phone);
        this.tietNamePet = findViewById(R.id.TIED_NamePet);
        this.tietSpeciesPet = findViewById(R.id.TIED_TypePet);
        this.tietDescription = findViewById(R.id.TIED_Status);
        this.tietTime = findViewById(R.id.TIED_Time);
        this.tietTimeHold = findViewById(R.id.TIED_TimeHold);
        this.tietService = findViewById(R.id.TIED_Service);
        this.ivPicture = findViewById(R.id.img_picture);

        this.bookingDetailViewModel = ViewModelProviders.of(this).get(BookingDetailViewModel.class);
    }
}