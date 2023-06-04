package com.example.quanlypet.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.ui.activity.QrCodeActivity;
import com.example.quanlypet.viewmodel.OAuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edUsername;
    private TextInputEditText edPassword;
    private CheckBox ckbNhoMK;
    private TextView tvSignup;
    private Button btnLogin;
    private TextView tvResetPass;
    private TextView tvErrors;
    private OAuthViewModel oauthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectView();
        actionView();
    }

    private void connectView(){
        oauthViewModel = ViewModelProviders.of(this).get(OAuthViewModel.class);

        edUsername = findViewById(R.id.ed_Username);
        edPassword = findViewById(R.id.ed_Password);
        ckbNhoMK = findViewById(R.id.ckb_nhoMK);
        tvResetPass = findViewById(R.id.tv_reset_pass);
        tvSignup = findViewById(R.id.tv_signup);
        btnLogin = findViewById(R.id.btn_login);
        tvErrors = findViewById(R.id.tv_errors);
        tvErrors.setText("");

        boolean rememberValue = SessionUtils.get(this, DataStatic.SESSION.KEY.REMEMBER_ACCOUNT, false);
        ckbNhoMK.setChecked(rememberValue);
        if(rememberValue){
            edUsername.setText(SessionUtils.get(this, DataStatic.SESSION.KEY.USERNAME, ""));
            edPassword.setText(SessionUtils.get(this, DataStatic.SESSION.KEY.PASSWORD, ""));
        }
    }

    private void actionView(){
        tvSignup.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignupUsersActivity.class));
        });
        btnLogin.setOnClickListener(view -> {
            checkLogin();
        });
    }

    public void checkLogin() {
        String username = edUsername.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (username.isEmpty()|| password.isEmpty()) {
            tvErrors.setTextColor(Color.RED);
            tvErrors.setText("Không được bỏ trống !");
        }else {
            LiveData<CommonResponse<String>> lvData = oauthViewModel.login(new User(username, password));
            lvData.observe(LoginActivity.this, data -> {
                if(data.getStatus()){
                    rememberUser(username, password, ckbNhoMK.isChecked());
                    SessionUtils.set(this, DataStatic.SESSION.KEY.USER_ID, Integer.parseInt(data.getData()));
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    tvErrors.setTextColor(Color.GREEN);
                    tvErrors.setText("Đăng nhập thành công.");
                    loadUserLoginInfo();
                }else{
                    tvErrors.setTextColor(Color.RED);
                    tvErrors.setText("Tên đăng nhập hoặc mật khẩu không chính xác.");
                }
            });
        }
    }

    private void loadUserLoginInfo(){
        Integer uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<User>> lvData = oauthViewModel.userInfo(uid);
        lvData.observe(this, data -> {
            if(data.getStatus()){
                DataStatic.SESSION.USER_LOGIN = data.getData();
                DataStatic.SESSION.USER_LOGIN.decrypt();
                finish();
            }
        });
    }

    public void rememberUser(String user, String pass, boolean status) {
        SessionUtils.set(this, DataStatic.SESSION.KEY.USERNAME, user);
        SessionUtils.set(this, DataStatic.SESSION.KEY.PASSWORD, pass);
        SessionUtils.set(this, DataStatic.SESSION.KEY.REMEMBER_ACCOUNT, status);
    }

}