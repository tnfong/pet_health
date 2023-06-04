package com.example.quanlypet.ui.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.animal.AnimalAdapter;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.FileUtils;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.viewmodel.AnimalViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnimalActivity extends AppCompatActivity implements AnimalAdapter.Callback{

    private AnimalViewModel animalViewModel;
    private RecyclerView rcvAnimal;
    private Toolbar Tbr;
    private ArrayList<Animal> arrayList = new ArrayList<>();
    private AnimalAdapter adapterAnimal;
    private CircleImageView imgAnhup;
    private SearchView searchView;
    private String imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        connectView();
        fill();
    }

    private void connectView(){

        animalViewModel = ViewModelProviders.of(this).get(AnimalViewModel.class);
        rcvAnimal = (RecyclerView) findViewById(R.id.rcv_animal);
        Tbr = findViewById(R.id.id_tollBarAnimal);
        setSupportActionBar(Tbr);
        getSupportActionBar().setTitle("Animal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tbr.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
    public void loadData(){
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);

        LiveData<CommonResponse<List<Animal>>> lvData = animalViewModel.list(uid);
        lvData.observe(AnimalActivity.this, data -> {
            if(data.getStatus()){
                arrayList.clear();
                arrayList.addAll(data.getData());
                adapterAnimal.setData(arrayList);
            }
        });
    }

    public void fill() {
        adapterAnimal = new AnimalAdapter(this, this);
        loadData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcvAnimal.setLayoutManager(layoutManager);
        rcvAnimal.setAdapter(adapterAnimal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meu_add_animal, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_animal).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterAnimal.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterAnimal.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_animal_menu:
                startActivity(new Intent(getApplicationContext(), AddAnimalActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && requestCode == RESULT_OK && data != null){
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            imgAnhup.setImageBitmap(bp);
        }

    }
    @Override
    public void Update(Animal animal) {
        Dialog dialog = new Dialog(AnimalActivity.this,com.google.android.material.R.style.Widget_Material3_MaterialCalendar_Fullscreen);
        dialog.setContentView(R.layout.dialog_update_animal);
        dialog.show();
        EditText upnameAnimal = dialog.findViewById(R.id.up_nameAnimal);
        EditText upageAnimal = dialog.findViewById(R.id.up_ageAnimal);
        EditText upspeciesAnimal = dialog.findViewById(R.id.up_speciesAnimal);
        EditText upDescription = dialog.findViewById(R.id.ed_description);
        imgAnhup = dialog.findViewById(R.id.up_img_anh);
        ImageView btnAlbumUp = dialog.findViewById(R.id.btn_album_up);
        Button btnUpDate = dialog.findViewById(R.id.btn_updateAnimal);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnAlbumUp.setOnClickListener(v ->{
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);

            chooseImage1.launch(i);
        });

        upnameAnimal.setText(animal.getName());
        ImageUtils.loadUrl(this, imgAnhup, animal.getAvatarUrl());

        upageAnimal.setText(String.valueOf(animal.getAge()));
        upspeciesAnimal.setText(animal.getSpecies());
        btnUpDate.setOnClickListener(v -> {
            String nameAnimal = upnameAnimal.getText().toString().trim();
            String description = upDescription.getText().toString().trim();
            int age = Integer.parseInt(upageAnimal.getText().toString().trim());
            String speciesAnimal = upspeciesAnimal.getText().toString().trim();
            if (nameAnimal.isEmpty() || speciesAnimal.isEmpty()) {
                Toast.makeText(getApplicationContext(), "ko dc de trong", Toast.LENGTH_SHORT).show();
            } else {
                animal.setName(nameAnimal);
                animal.setAvatarUri(imageUri);
                animal.setAge(age);
                animal.setSpecies(speciesAnimal);
                animal.setDescription(description);
                Integer uid = SessionUtils.get(AnimalActivity.this, DataStatic.SESSION.KEY.USER_ID, 0);
                animal.setIdUser(uid);
                LiveData<CommonResponse<Animal>> lvData = animalViewModel.save(animal);
                lvData.observe(this, data -> {
                    if(data.getStatus()){
                        dialog.cancel();
                    }
                });
            }
        });
        btnCancel.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.show();
    }

    ActivityResultLauncher<Intent> chooseImage1 = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data1 = result.getData();
                    Uri selectedImageUri1 = data1.getData();
                    if (null != selectedImageUri1) {
                        imageUri = FileUtils.getPath(AnimalActivity.this, selectedImageUri1);
                        imgAnhup.setImageURI(selectedImageUri1);
                    }
                }
            }
        });

}