package com.example.quanlypet.adapter.booking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.ui.fragment.BookWaitFragment;
import com.example.quanlypet.ui.fragment.BookCancelFragment;
import com.example.quanlypet.ui.fragment.BookConfirmFragment;
import com.example.quanlypet.ui.fragment.BookDoneFragment;

public class ViewPager2BookingAdapter extends FragmentStateAdapter {
    MainActivity activity;
    public ViewPager2BookingAdapter(MainActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment =null;
        switch (position) {
            case 0:
                fragment = new BookWaitFragment(activity);
                break;
            case 1:
                fragment = new BookConfirmFragment(activity);
                break;
            case 2:
                fragment = new BookDoneFragment(activity);
                break;
            case 3:
                fragment = new BookCancelFragment(activity);
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
