package com.example.quanlypet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.Bill;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.AddBillViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBillActivity extends AppCompatActivity {
    private AddBillViewModel addBillViewModel;
    private Toolbar toolbar;
    private TextInputEditText edUsername, edUserPhone, edPrice, edNote, edCreatedDate;
    private Button btnAddBill;
    private Bill bill = new Bill();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        connectView();
        actionView();
        initBill();
    }

    private void connectView(){
        toolbar = findViewById(R.id.id_tollBar_addBill);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm bill");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edUsername = findViewById(R.id.ed_Username);
        edUserPhone = findViewById(R.id.ed_Userphone);
        edPrice = findViewById(R.id.ed_price);
        edNote = findViewById(R.id.ed_note);
        edCreatedDate = findViewById(R.id.ed_created_date);
        btnAddBill = findViewById(R.id.btn_Add_bill);
        addBillViewModel = ViewModelProviders.of(this).get(AddBillViewModel.class);
    }

    private void actionView(){
        btnAddBill.setOnClickListener(v -> {
            double price = Double.parseDouble(edPrice.getText().toString().trim());
            String note = edNote.getText().toString().trim();
            if (edPrice.getText().toString().trim().isEmpty() || note.isEmpty()) {
                Toast.makeText(getApplicationContext(), "ban phai nhap het", Toast.LENGTH_SHORT).show();
            } else if(price < bill.getPrice()) {
                Toast.makeText(getApplicationContext(), "Giá tiền không được dưới "+bill.getPrice(), Toast.LENGTH_SHORT).show();
            } else {
                bill.setPrice(price);
                bill.setNote(note);
                LiveData<CommonResponse<Bill>> lvData = addBillViewModel.save(SessionUtils.get(AddBillActivity.this, DataStatic.SESSION.KEY.USER_ID, 0), bill);
                lvData.observe(this, data -> {
                    if(data.getStatus()){
                        finish();
                    }
                });
            }
        });
    }

    private void initBill(){
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        Integer idBook = getIntent().getIntExtra(DataStatic.INTENT.ID_BOOK, 0);
        bill.setIdBook(idBook);
        bill.setIdUser(uid);
        LiveData<CommonResponse<Bill>> lvData = addBillViewModel.init(uid, idBook);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                bill.setData(data.getData());
                bill.decrypt();
                showInfo();
            }
        });
    }

    private void showInfo(){
        edUsername.setText(bill.getUserFullName());
        edUserPhone.setText(bill.getUserPhone());
        edPrice.setText(String.valueOf(bill.getPrice()));
        edNote.setText(bill.getNote());
        Date mDate = (bill.getCreatedDate() == null) ? new Date() : bill.getCreatedDate();
        edCreatedDate.setText(sdf.format(mDate));
    }
}