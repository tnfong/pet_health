package com.example.quanlypet.ui.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlypet.MyApplication;
import com.example.quanlypet.R;
import com.example.quanlypet.adapter.spinner.SpinnerAnimal;
import com.example.quanlypet.adapter.spinner.SpinnerDoctor;
import com.example.quanlypet.adapter.spinner.SpinnerService;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.FileUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.model.Service;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.AddBookingViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddBookingActivity extends AppCompatActivity {
    private AddBookingViewModel addBookingViewModel;
    private ImageView imgClose;
    private Toolbar toolbar;
    private Spinner spnDoctor, spnPet, spnService;
    private TextInputEditText TIEDNameDoctor;
    private TextInputEditText TIEDPhoneNumber;
    private TextInputEditText TIEDNamePet;
    private TextInputEditText TIEDTypePet;
    private TextInputEditText TIEDStatus;
    private CircleImageView imgPicture;
    private ImageView btnAlbum;
    private TextInputEditText TIEDTime;
    private TextInputEditText TIEDTimeHold;
    private Button btnBooking;
    SpinnerAnimal adapterSPNAnimal;
    SpinnerDoctor adapterSPNDoctor;
    SpinnerService adapterService;
    private String noikham;
    private Book bookInfo = new Book();

    List<Animal> listAnimal = new ArrayList<>();
    List<User> listDoctor = new ArrayList<>();
    List<Service> listService = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    int mYear, mMonth, mDate, mHour, mMinute, mMinute2;
    int uid = 0;
    String photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        connectView();

        DatePickerDialog.OnDateSetListener date = (datePicker, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDate = dayOfMonth;
        };
        TimePickerDialog.OnTimeSetListener time = ((timePicker, hourOfDay, minute) -> {
            mHour = hourOfDay;
            mMinute = minute;
            mMinute2 = minute + 60;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDate, mHour, mMinute);
            GregorianCalendar calendar2 = new GregorianCalendar(mYear, mMonth, mDate, mHour, mMinute2);
            TIEDTime.setText(dateFormat.format(calendar.getTime()));
            TIEDTimeHold.setText(dateFormat.format(calendar2.getTime()));
        });

        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                chooseImg.launch(i);
            }
        });
        dismissKeyboardShortcutsHelper();
        TIEDTime.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDate = calendar.get(Calendar.DAY_OF_MONTH);
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar, date, mYear, mMonth, mDate);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar, time, mHour, mMinute, true);
            timePickerDialog.show();
            datePickerDialog.show();
        });

        LiveData<CommonResponse<List<Animal>>> lvAnimalData = addBookingViewModel.listAnimal(uid);
        lvAnimalData.observe(AddBookingActivity.this, data -> {
            if(data.getStatus()){
                listAnimal.clear();
                listAnimal.addAll(data.getData());
                adapterSPNAnimal.setData(listAnimal);
                spnPet.setAdapter(adapterSPNAnimal);
            }
        });

        LiveData<CommonResponse<List<User>>> lvUserData = addBookingViewModel.listUser(uid, 2);
        lvUserData.observe(AddBookingActivity.this, data -> {
            if(data.getStatus()){
                listDoctor.clear();
                listDoctor.addAll(data.getData());
                listDoctor.forEach(User::decrypt);
                adapterSPNDoctor.setDATA(listDoctor);
                spnDoctor.setAdapter(adapterSPNDoctor);
            }
        });

        loadDataService();

        TIEDNameDoctor.setFocusable(false);
        TIEDNameDoctor.setFocusableInTouchMode(false);
        TIEDNamePet.setFocusable(false);
        TIEDNamePet.setFocusableInTouchMode(false);
        TIEDPhoneNumber.setFocusable(false);
        TIEDPhoneNumber.setFocusableInTouchMode(false);
        TIEDTypePet.setFocusable(false);
        TIEDTypePet.setFocusableInTouchMode(false);

        initSpinner();
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBooking();
            }
        });
    }

    ActivityResultLauncher<Intent> chooseImg = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectImgUri = data.getData();
                        if (selectImgUri != null) {
                            imgPicture.setImageURI(selectImgUri);
                            photoUri = FileUtils.getPath(AddBookingActivity.this, selectImgUri);
                        }
                    }
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPicture.setImageBitmap(bitmap);

        }
    }

    private void addBooking() {

        String strTT = TIEDStatus.getText().toString();
        String strTime = TIEDTime.getText().toString();
        String strTimeHold = TIEDTimeHold.getText().toString();
        bookInfo.setData(uid, strTT, photoUri, strTime, strTimeHold, 3);
        LiveData<CommonResponse<Book>> lvData = addBookingViewModel.save(bookInfo);
        lvData.observe(AddBookingActivity.this, data -> {
            if(data.getStatus()){
                sendNotification();
                showSnackbar();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);
            }else{
                showSnackbar2();
            }
        });
    }

    public void initSpinner() {
        spnDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bookInfo.setIdDoctor(listDoctor.get(position).getId());
                TIEDNameDoctor.setText(listDoctor.get(position).getFullName());
                TIEDPhoneNumber.setText(listDoctor.get(position).getPhone());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bookInfo.setIdAnimal(listAnimal.get(position).getId());
                TIEDNamePet.setText(listAnimal.get(position).getName());
                TIEDTypePet.setText(listAnimal.get(position).getSpecies());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                bookInfo.setIdService(listService.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void connectView() {
        spnDoctor = findViewById(R.id.spn_doctor);
        TIEDNameDoctor = findViewById(R.id.TIED_NameDoctor);
        TIEDPhoneNumber = findViewById(R.id.TIED_PhoneNumber);
        spnPet = findViewById(R.id.spn_Pet);
        toolbar = findViewById(R.id.tbl_booking);
        TIEDNamePet = findViewById(R.id.TIED_NamePet);
        TIEDTypePet = findViewById(R.id.TIED_TypePet);
        TIEDStatus = findViewById(R.id.TIED_Status);
        imgPicture = findViewById(R.id.img_picture);
        btnAlbum = findViewById(R.id.btn_album);
        btnBooking = findViewById(R.id.btn_booking);
        TIEDTime = findViewById(R.id.TIED_Time);
        TIEDTimeHold = findViewById(R.id.TIED_TimeHold);
        spnService = findViewById(R.id.spn_service);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đặt Lịch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);

        adapterSPNAnimal = new SpinnerAnimal(this);
        adapterSPNDoctor = new SpinnerDoctor(this);
        adapterService = new SpinnerService(this, listService);
        spnService.setAdapter(adapterService);

        uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        addBookingViewModel = ViewModelProviders.of(this).get(AddBookingViewModel.class);

    }

    private void loadDataService(){
        LiveData<CommonResponse<List<Service>>> lvAnimalData = addBookingViewModel.listService(uid);
        lvAnimalData.observe(AddBookingActivity.this, data -> {
            if(data.getStatus()){
                listService.clear();
                listService.addAll(data.getData());
                adapterService.notifyDataSetChanged();
            }
        });
    }

    public void showSnackbar() {
        connectView();
        Snackbar snackbar = Snackbar.make(btnBooking, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(R.layout.custom_snackbar, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }

    public void showSnackbar2() {
        connectView();
        Snackbar snackbar = Snackbar.make(btnBooking, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(R.layout.custom_snackbar2, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        snackbarLayout.addView(custom, 0);
        snackbar.show();

    }

    public void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources   (), R.drawable.pet_shop);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle("Thông báo từ PetVip")
                .setContentText("Đặt Lịch Thành Công")
                .setSmallIcon(R.drawable.pet_shop)
                .setLargeIcon(bitmap)
                .setSound(uri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(getResources().getColor(R.color.purple_2001))
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getNotificationID(), notification);
    }

    private int getNotificationID() {
        return (int) new Date().getTime();
    }
}