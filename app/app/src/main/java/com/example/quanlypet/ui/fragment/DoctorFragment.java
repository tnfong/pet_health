package com.example.quanlypet.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.adapter.doctor.DoctorAdapter;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.FileUtils;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.ui.activity.AddDoctorActivity;
import com.example.quanlypet.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DoctorFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private DoctorAdapter adapter;
    private SearchView searchDoctor;
    String username;
    int uid;
    private MainActivity activity;

    public DoctorFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_docter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = SessionUtils.get(getActivity(), DataStatic.SESSION.KEY.USERNAME, "");
        uid = SessionUtils.get(getActivity(), DataStatic.SESSION.KEY.USER_ID, 0);
        recyclerView = view.findViewById(R.id.rcv_docter);
        floatingActionButton = view.findViewById(R.id.floatingbutton);
        searchDoctor = view.findViewById(R.id.search_doctor);

        adapter = new DoctorAdapter(getActivity());

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddDoctorActivity.class);
            intent.putExtra(DataStatic.INTENT.ID, 0);
            startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if (UserUtils.isAdmin()){
            floatingActionButton.setVisibility(View.VISIBLE);
        }

        searchDoctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        loadDoctorList();
    }

    private void loadDoctorList(){
        LiveData<CommonResponse<List<User>>> lvData = this.activity.getMainViewModel().doctorList(uid);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                adapter.setDataDocter(data.getData());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDoctorList();
    }
}