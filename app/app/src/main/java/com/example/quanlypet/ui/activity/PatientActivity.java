package com.example.quanlypet.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.patient.PatientAdapter;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Guest;
import com.example.quanlypet.viewmodel.PatientViewModel;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {
    private PatientViewModel patientViewModel;
    private Toolbar toolbar;
    private RecyclerView rcvPatient;
    private List<Guest> guests = new ArrayList<>();
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        connectView();
        getGuestList();
    }

    private void connectView(){
        this.patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
        toolbar = findViewById(R.id.id_tollBar);
        rcvPatient = findViewById(R.id.rcv_patient);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Xem bệnh án");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new PatientAdapter(this, guests);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcvPatient.setLayoutManager(layoutManager);
        rcvPatient.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getGuestList();
    }

    public void getGuestList(){
        Integer idAnimal = getIntent().getIntExtra(DataStatic.INTENT.ID_ANIMAL, 0);
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<Guest>>> lvData = patientViewModel.list(uid, idAnimal);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                guests.clear();
                guests.addAll(data.getData());
                adapter.notifyDataSetChanged();
            }
        });
    }

}