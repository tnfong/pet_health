package com.example.quanlypet.adapter.viewpager2;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.ui.fragment.BillFragment;
import com.example.quanlypet.ui.fragment.BookFragment;
import com.example.quanlypet.ui.fragment.DoctorFragment;
import com.example.quanlypet.ui.fragment.HomeFragment;

import com.example.quanlypet.ui.fragment.UsersFragment;


public class ViewPager2Adapter extends FragmentStateAdapter {

    MainActivity activity;

    public ViewPager2Adapter(MainActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            default:
                return new HomeFragment(activity);
            case 1:
                return new DoctorFragment(activity);
            case 2:
                return new BookFragment(activity);
            case 3:
                return (UserUtils.isDoctor()) ? new UsersFragment(activity) : new BillFragment(activity);
            case 4:
                return new UsersFragment(activity);
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
