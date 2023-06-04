package com.example.quanlypet.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.ChangePasswordViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePasswordActivity extends AppCompatActivity {
    private ChangePasswordViewModel changePasswordViewModel;
    private TextInputEditText nowPass, newPass, newPassConfirm;
    private TextView tvErrors;
    private TextView tvExit;
    private TextView imgSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        connectView();
        actionView();
    }

    private void connectView(){
        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        nowPass = findViewById(R.id.ed_nowPass);
        newPass = findViewById(R.id.newPass);
        newPassConfirm = findViewById(R.id.newPassAganin);
        tvErrors = findViewById(R.id.tv_errors);
        tvExit = findViewById(R.id.tv_exit);
        imgSave = findViewById(R.id.img_save);
        tvErrors.setText("");
    }

    private void actionView(){

        tvExit.setOnClickListener(view1 -> {
            finish();
        });

        imgSave.setOnClickListener(view1 -> {
            Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
            if (validate() > 0) {
                LiveData<CommonResponse<User>> lvData = changePasswordViewModel.changePass(uid, newPass.getText().toString());
                lvData.observe(ChangePasswordActivity.this, data -> {
                    if(data.getStatus()){
                        tvErrors.setText("Đổi mật khẩu thành công.");
                        finish();
                    }
                });
            }
        });
    }

    public int validate(){
        int check = 1;
        if (nowPass.getText().toString().trim().isEmpty() ||
                newPass.getText().toString().trim().isEmpty() ||
                newPassConfirm.getText().toString().isEmpty()){
            tvErrors.setText("Không để trống !");
            check = -1;
        }else {
            String MKcu = SessionUtils.get(this, DataStatic.SESSION.KEY.PASSWORD, "");
            String MKmoi = newPass.getText().toString();
            String MKlai = newPassConfirm.getText().toString();
            if (!MKcu.equals(nowPass.getText().toString())){
                tvErrors.setText("Mật khẩu hiện tại không đúng !");
                check = -1;
            }
            if (!MKmoi.equals(MKlai)){
                tvErrors.setText("Mật khẩu mới không trùng khớp !");
                check = -1;
            }
        }
        return check;
    }
}