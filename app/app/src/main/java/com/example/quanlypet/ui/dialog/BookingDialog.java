package com.example.quanlypet.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import com.example.quanlypet.R;
import com.example.quanlypet.model.Book;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class BookingDialog {
    private Activity activity;
    private Dialog dialog;
    private Toolbar toolbar;
    private Spinner spnDoctor;
    private TextInputLayout TIPNameDoctor, TIPPhoneNumber, TIPNamePet, TIPTypePet, TIPStatus, TIPTime, TIPAddress, TIPService;
    private TextInputEditText TIEDNameDoctor, TIEDPhoneNumber, TIEDNamePet, TIEDTypePet, TIEDStatus, TIEDTime, TIEDAddress, TIEDService;
    private Spinner spnPet;
    private ImageView imgPicture;
    private Button btnCamera, btnAlbum;
    private Button btnUpdate, btnHuy;
    private String noikham;
    private Book book;


    public BookingDialog(Activity activity, Book book){
        this.activity = activity;
        this.book = book;
        this.dialog = new Dialog(this.activity);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_update_booking);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        this.connectView();
    }

    private void connectView(){
        toolbar = dialog.findViewById(R.id.tbl_booking);
        toolbar.setTitle("Chi tiết Lịch Đặt");
        spnDoctor = (Spinner) dialog.findViewById(R.id.spn_doctor);
        TIPNameDoctor = (TextInputLayout) dialog.findViewById(R.id.TIP_NameDoctor);
        TIEDNameDoctor = (TextInputEditText) dialog.findViewById(R.id.TIED_NameDoctor);
        TIPPhoneNumber = (TextInputLayout) dialog.findViewById(R.id.TIP_PhoneNumber);
        TIEDPhoneNumber = (TextInputEditText) dialog.findViewById(R.id.TIED_PhoneNumber);
        spnPet = (Spinner) dialog.findViewById(R.id.spn_Pet);
        TIPNamePet = (TextInputLayout) dialog.findViewById(R.id.TIP_NamePet);
        TIEDNamePet = (TextInputEditText) dialog.findViewById(R.id.TIED_NamePet);
        TIPTypePet = (TextInputLayout) dialog.findViewById(R.id.TIP_TypePet);
        TIEDTypePet = (TextInputEditText) dialog.findViewById(R.id.TIED_TypePet);
        TIPStatus = (TextInputLayout) dialog.findViewById(R.id.TIP_Status);
        TIEDStatus = (TextInputEditText) dialog.findViewById(R.id.TIED_Status);
        imgPicture = (ImageView) dialog.findViewById(R.id.img_picture);
        TIPTime = (TextInputLayout) dialog.findViewById(R.id.TIP_Time);
        TIEDTime = (TextInputEditText) dialog.findViewById(R.id.TIED_Time);
        TIPAddress = (TextInputLayout) dialog.findViewById(R.id.TIP_Address);
        TIEDAddress = (TextInputEditText) dialog.findViewById(R.id.TIED_Address);
        TIPService = (TextInputLayout) dialog.findViewById(R.id.TIP_Service);
        TIEDService = (TextInputEditText) dialog.findViewById(R.id.TIED_Service);
        btnUpdate = (Button) dialog.findViewById(R.id.btn_update);
        btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
        TIEDStatus.setText(book.getStatus());
        TIEDAddress.setText(book.getAddress());
        TIEDTime.setText(book.getTimeText());
        TIEDService.setText(book.getServiceName());
        TIEDService.setFocusable(false);
        TIEDService.setFocusableInTouchMode(false);
        TIEDNameDoctor.setFocusable(false);
        TIEDNameDoctor.setFocusableInTouchMode(false);
        TIEDNamePet.setFocusable(false);
        TIEDNamePet.setFocusableInTouchMode(false);
        TIEDPhoneNumber.setFocusable(false);
        TIEDPhoneNumber.setFocusableInTouchMode(false);
        TIEDTypePet.setFocusable(false);
        TIEDTypePet.setFocusableInTouchMode(false);
        btnUpdate.setVisibility(View.INVISIBLE);
        btnHuy.setVisibility(View.INVISIBLE);
    }

    public void show(){
        this.dialog.show();
    }

    public void hide(){
        this.dialog.dismiss();
    }
}
