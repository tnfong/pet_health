package com.example.quanlypet.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.ui.activity.AnimalActivity;
import com.example.quanlypet.ui.activity.ChatActivity;
import com.example.quanlypet.ui.activity.ListUserActivity;
import com.example.quanlypet.ui.activity.UserInfoActivity;
import com.example.quanlypet.ui.welcome.ChangePasswordActivity;
import com.example.quanlypet.ui.welcome.LoginActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class UsersFragment extends Fragment {
    private LinearLayout lnInforAccount, lnAnimalManager, lnUserManager,lnScanQr, lnChat, lnChangePass, lnLogOut;
    private TextView usersName;
    private Animation animation;
    private MainActivity activity;

    public UsersFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usersName = view.findViewById(R.id.usersName);
        lnInforAccount = view.findViewById(R.id.ln_inforAccount);
        lnAnimalManager = view.findViewById(R.id.ln_animalManager);
        lnUserManager = view.findViewById(R.id.ln_userManager);
        lnChangePass = view.findViewById(R.id.ln_changePass);
        lnScanQr = view.findViewById(R.id.ln_scan_qr);
        lnLogOut = view.findViewById(R.id.ln_logOut);
        lnChat = view.findViewById(R.id.ln_chat);
        animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_down);

        usersName.setText(DataStatic.SESSION.USER_LOGIN.getFullName());
        if (UserUtils.isAdmin()) {
            lnUserManager.setVisibility(View.VISIBLE);
        }
        if(UserUtils.isUser()){
            lnAnimalManager.setVisibility(View.VISIBLE);
        }

        lnAnimalManager.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), AnimalActivity.class));
        });
        lnInforAccount.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), UserInfoActivity.class));
        });
        lnUserManager.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), ListUserActivity.class));
        });
        lnScanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
            }
        });
        lnChangePass.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), ChangePasswordActivity.class));
        });
        lnChat.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), ChatActivity.class));
        });
        lnLogOut.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        });
        lnLogOut.setAnimation(animation);
        lnChangePass.setAnimation(animation);
        lnUserManager.setAnimation(animation);
        lnInforAccount.setAnimation(animation);
        lnAnimalManager.setAnimation(animation);
        lnScanQr.setAnimation(animation);
        lnChat.setAnimation(animation);
    }
}