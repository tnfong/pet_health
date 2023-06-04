package com.example.quanlypet.ui.welcome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.quanlypet.R;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.ValidationUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.viewmodel.OAuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SignupUsersActivity extends AppCompatActivity {
    private OAuthViewModel oauthViewModel;
    private TextView tvBack;
    private TextInputEditText edUsername;
    private TextInputEditText edName;
    private TextInputEditText edEmail;
    private TextInputEditText edPhone;
    private RadioButton rdoMale;
    private RadioButton rdoFemale;
    private TextInputEditText edPassword;
    private TextInputEditText edRePassword;
    private TextView tvErrors;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_users);

        connectView();
        actionView();
    }

    private void connectView(){
        oauthViewModel = ViewModelProviders.of(this).get(OAuthViewModel.class);
        tvBack = findViewById(R.id.tv_exit);
        edUsername = findViewById(R.id.ed_Username);
        edName = findViewById(R.id.ed_Name);
        edEmail = findViewById(R.id.ed_Email);
        edPhone = findViewById(R.id.ed_Phone);
        rdoMale = findViewById(R.id.rdo_Male);
        rdoFemale = findViewById(R.id.rdo_Female);
        edPassword = findViewById(R.id.ed_Password);
        edRePassword = findViewById(R.id.ed_RePassword);
        tvErrors = findViewById(R.id.tv_errors);
        btnSignup = findViewById(R.id.btn_Signup);
        rdoMale.setChecked(true);
        tvErrors.setText("");
    }

    private void actionView(){

        tvBack.setOnClickListener(view -> {
            finish();
        });
        btnSignup.setOnClickListener(view -> {
            if (validate()>0){
                String username = edUsername.getText().toString().trim();
                String fullName = edName.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                int gender = 0;
                if (rdoMale.isChecked()){
                    gender = 0;
                } else if (rdoFemale.isChecked()){
                    gender = 1;
                }
                User user = new User();
                user.setUsername(username);
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPhone(phone);
                user.setPassword(password);
                user.setGender(gender);

                LiveData<CommonResponse<User>> lvData = oauthViewModel.register(user);
                lvData.observe(SignupUsersActivity.this, data -> {
                    if(data.getStatus()){
                        finish();
                    }
                });
            }
        });
    }
    public int validate(){
        String importName = edUsername.getText().toString().trim();
        String fullName = edName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String rePass = edRePassword.getText().toString().trim();
        int check = 1;
        if (importName.isEmpty() || fullName.isEmpty() || email.isEmpty() ||
                phone.isEmpty() || password.isEmpty()|| rePass.isEmpty()) {
            tvErrors.setText("Không được để trống!");
            check = -1;
        } else if(!ValidationUtils.isPhone(phone)) {
            Toast.makeText(this, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
        } else if(!ValidationUtils.isMail(email)) {
            Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
        } else {
            if (!rePass.equals(password)){
                tvErrors.setText("Mật khẩu không khớp.");
                check = -1;
            }
        }
        return check;
    }
}