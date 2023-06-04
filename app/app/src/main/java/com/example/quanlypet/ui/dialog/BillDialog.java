package com.example.quanlypet.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.model.Bill;
import com.example.quanlypet.model.User;
import com.example.quanlypet.ui.activity.AddAnimalActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class BillDialog {
    private MainActivity activity;
    private Dialog dialog;
    private TextInputEditText edUsername, edUserphone, edPrice, edNote;
    private Button btnUpdateBill;
    private Button btnCancel;
    private Bill bill;


    public BillDialog(MainActivity activity, Bill bill){
        this.activity = activity;
        this.bill = bill;
        this.dialog = new Dialog(this.activity);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_update_bill);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        this.connectView();
        this.actionView();
    }

    private void connectView(){
        edUsername = dialog.findViewById(R.id.ed_Username);
        edUserphone = dialog.findViewById(R.id.ed_Userphone);
        edPrice = dialog.findViewById(R.id.ed_price);
        edNote = dialog.findViewById(R.id.ed_note);
        btnUpdateBill = dialog.findViewById(R.id.btn_Update_bill);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        if (UserUtils.isAdmin()){
            btnUpdateBill.setVisibility(View.VISIBLE);
        }
        edNote.setText(bill.getNote());
        edPrice.setText(bill.getPrice() + "");
        edUsername.setText(bill.getUserFullName());
        edUserphone.setText(bill.getUserPhone());
    }

    private void actionView(){
        Integer uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0);
        btnUpdateBill.setOnClickListener(v -> {
            if (edPrice.getText().toString().trim().isEmpty()||edNote.getText().toString().trim().isEmpty()) {
                Toast.makeText(activity, "ko dc de trong", Toast.LENGTH_SHORT).show();
            } else {
                bill.setPrice(Double.parseDouble(edPrice.getText().toString().trim()));
                bill.setNote(edNote.getText().toString().trim());
                LiveData<CommonResponse<Bill>> lvData = activity.getMainViewModel().billSave(uid, bill);
                lvData.observe(activity, data -> {
                    if(data.getStatus()){
                        hide();
                    }
                });
            }
        });
        btnCancel.setOnClickListener(v -> {
            dialog.cancel();
        });
    }

    public void show(){
        this.dialog.show();
    }

    public void hide(){
        this.dialog.dismiss();
    }
}
