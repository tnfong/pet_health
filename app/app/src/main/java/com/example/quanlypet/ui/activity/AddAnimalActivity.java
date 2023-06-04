package com.example.quanlypet.ui.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.FileUtils;
import com.example.quanlypet.common.utils.PermissionUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.viewmodel.AddAnimalViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddAnimalActivity extends AppCompatActivity {
    private AddAnimalViewModel addAnimalViewModel;
    private Toolbar Tbr;
    private EditText etName, etAge, etSpecies, etDescription;
    private CircleImageView imgAnh;
    private ImageView btnAlbum;
    private Button btnAdd;
    private Button btnCancel;
    private String avatarUri;
    int REQUEST_CODE_ALBUM = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        connectView();
        actionView();
    }

    private void connectView(){
        this.addAnimalViewModel = ViewModelProviders.of(this).get(AddAnimalViewModel.class);
        Tbr = findViewById(R.id.id_tollBar_addAnimal);
        setSupportActionBar(Tbr);
        getSupportActionBar().setTitle("Add Animal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tbr.setTitleTextColor(Color.WHITE);
        etName = findViewById(R.id.ed_nameAnimal);
        etAge = findViewById(R.id.ed_ageAnimal);
        etSpecies = findViewById(R.id.ed_speciesAnimal);
        etDescription = findViewById(R.id.ed_description);
        btnAlbum = findViewById(R.id.btn_album);
        imgAnh = findViewById(R.id.img_anh);
        btnAdd = findViewById(R.id.btn_addAnimal);
    }

    private void actionView(){

        btnAlbum.setOnClickListener(v ->{
            if(PermissionUtils.readExternalStorage(this)) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                chooseImage.launch(i);
            }
        });

        btnAdd.setOnClickListener(v -> {
            String namean = etName.getText().toString().trim();
            int age = Integer.parseInt(etAge.getText().toString().trim());
            String species = etSpecies.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            if (namean.isEmpty() || species.isEmpty()) {
                Toast.makeText(getApplicationContext(), "ko dc de trong", Toast.LENGTH_SHORT).show();
            } else {
                Integer uid = SessionUtils.get(AddAnimalActivity.this, DataStatic.SESSION.KEY.USER_ID, 0);
                Animal animal = new Animal(uid, namean, avatarUri, age, species,1, description);
                LiveData<CommonResponse<Animal>> lvData = addAnimalViewModel.save(animal);
                lvData.observe(AddAnimalActivity.this, data -> {
                    if(data.getStatus()){
                        finish();
                    }
                });
            }
        });
    }

// chọn ảnh của đối tượng animal
    ActivityResultLauncher<Intent> chooseImage = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        imgAnh.setImageURI(selectedImageUri);
                        avatarUri = FileUtils.getPath(AddAnimalActivity.this, selectedImageUri);
                    }
                }
            }
        });
}