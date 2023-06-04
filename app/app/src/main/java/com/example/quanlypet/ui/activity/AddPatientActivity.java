package com.example.quanlypet.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Guest;
import com.example.quanlypet.viewmodel.AddPatientViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;


public class AddPatientActivity extends AppCompatActivity {
    private AddPatientViewModel addPatientViewModel;
    private Button btnAddPatient;
    private TextInputEditText edSysptoms;
    private TextInputEditText edDiagnostic;
    private TextInputEditText edInstructions;
    private Toolbar toolbar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;
    private Guest guest = new Guest();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        connectView();
        actionView();
    }

    private void connectView(){
        this.addPatientViewModel = ViewModelProviders.of(this).get(AddPatientViewModel.class);
        btnAddPatient = findViewById(R.id.btn_addPatient);
        edSysptoms = findViewById(R.id.ed_Sysptoms);
        edDiagnostic = findViewById(R.id.ed_Diagnostic);
        edInstructions = findViewById(R.id.ed_Instructions);
        toolbar = findViewById(R.id.id_tollBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm Bệnh án");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void actionView(){
        btnAddPatient.setOnClickListener(v->{
            String sysptems = edSysptoms.getText().toString().trim();
            String diagnostic = edDiagnostic.getText().toString().trim();
            String instructions = edInstructions.getText().toString().trim();
            if(sysptems.isEmpty() || diagnostic.isEmpty()){
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }else{
                guest.setSymptoms(sysptems);
                guest.setDiagnostic(diagnostic);
                guest.setInstructions(instructions);
                saveGuest();
            }
        });
    }

    private void saveGuest(){
        Integer idAnimal = getIntent().getIntExtra(DataStatic.INTENT.ID_ANIMAL, 0);
        Integer idBook = getIntent().getIntExtra(DataStatic.INTENT.ID_BOOK, 0);
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        guest.setIdAnimal(idAnimal);
        guest.setIdBook(idBook);
        guest.setIdDoctor(uid);

        LiveData<CommonResponse<Guest>> lvData = addPatientViewModel.save(uid, guest);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                finish();
            }
        });
    }
}