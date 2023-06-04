package com.example.quanlypet.ui.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.adapter.booking.ViewPager2BookingAdapter;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BookFragment extends Fragment {

    private TabLayout tablayout;
    private TabItem tab1;
    private TabItem tab2;
    private TabItem tab3;
    private TabItem tab4;
    private ViewPager2 viewpagerTablayout;
    private MainActivity activity;

    public BookFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tablayout = view.findViewById(R.id.tablayout);
        tab1 = view.findViewById(R.id.tab1);
        tab2 = view.findViewById(R.id.tab2);
        tab3 = view.findViewById(R.id.tab3);
        tab4 = view.findViewById(R.id.tab4);
        viewpagerTablayout = view.findViewById(R.id.viewpager_tablayout);
        ViewPager2BookingAdapter viewPager2_booking_adapter = new ViewPager2BookingAdapter(activity);
        viewpagerTablayout.setAdapter(viewPager2_booking_adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tablayout, viewpagerTablayout, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Chờ xác nận");
                        break;
                    case 1:
                        tab.setText("Đang xử lý");
                        break;
                    case 2:
                        tab.setText("Hoàn thành");
                        break;
                    case 3:
                        tab.setText("Bị hủy");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }
}
