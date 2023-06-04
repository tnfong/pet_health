package com.example.quanlypet.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.adapter.viewpager2.SlideAdapterHome;

import com.example.quanlypet.adapter.booking.BookingAdapter;
import com.example.quanlypet.adapter.booking.BookingAdminAdapter;

import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.model.Analysis;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.model.Config;
import com.example.quanlypet.model.Photo;
import com.example.quanlypet.ui.activity.AddBookingActivity;
import com.example.quanlypet.ui.activity.ChatActivity;
import com.example.quanlypet.ui.activity.ListDoctorActivity;
import com.example.quanlypet.viewmodel.MainViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private MainViewModel mainViewModel;
    private LinearLayout linerBooking, linerAmbulance, linerMess;
    private ViewPager vpr;
    private CircleIndicator circleIndicator;
    private SlideAdapterHome slideAdapter;
    private List<Photo> photoList;
    private Timer timer;
    private Animation animation;
    RecyclerView rvNear;
    List<Book> list;
    List<Analysis> analysisMonth = new ArrayList<>(), analysisWeek= new ArrayList<>();
    BookingAdapter adapter;
    TextView titleNear;
    BookingAdminAdapter adapter2;
    ArrayList<BarEntry> beMonth = new ArrayList<>(), beWeek = new ArrayList<>();
    private BarChart bcMonth, bcWeek;
    private MainActivity activity;
    BarDataSet bdsMonth, bdsWeek;
    BarData bdMonth, bdWeek;

    public HomeFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        connectView(view);
        actionView();
    }

    private void connectView(View view){
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        linerBooking = view.findViewById(R.id.liner_booking);
        linerAmbulance = view.findViewById(R.id.liner_ambulance);
        linerMess = view.findViewById(R.id.liner_mess);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);

        linerMess.setAnimation(animation);
        linerBooking.setAnimation(animation);
        linerAmbulance.setAnimation(animation);
        titleNear = view.findViewById(R.id.titleNear);
        list = new ArrayList<>();
        rvNear = view.findViewById(R.id.recy_bookingNear);
        adapter = new BookingAdapter(activity);
        adapter2 = new BookingAdminAdapter(list, activity);

        vpr = view.findViewById(R.id.vpr);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        bcMonth = view.findViewById(R.id.bc_month);
        bcWeek = view.findViewById(R.id.bc_week);
        photoList = getListPhoto();
        slideAdapter = new SlideAdapterHome(getContext(), photoList);
        vpr.setAdapter(slideAdapter);
        circleIndicator.setViewPager(vpr);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlide();
        beMonth = new ArrayList<>();

        if (UserUtils.isAdmin()){
            bcMonth.setVisibility(View.VISIBLE);
            bcWeek.setVisibility(View.VISIBLE);
        } else {
            bcMonth.setVisibility(View.GONE);
            bcWeek.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvNear.setAdapter(adapter2);
        rvNear.setLayoutManager(linearLayoutManager);

        if (UserUtils.isAdmin() || UserUtils.isDoctor()){
            titleNear.setText("Lịch Đang Chờ Gần Đây");
            linerBooking.setVisibility(View.GONE);
            linerAmbulance.setVisibility(View.GONE);
            linerMess.setVisibility(View.GONE);
        }


    }

    private void actionView(){
        linerBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddBookingActivity.class);
                startActivity(i);
            }
        });

        linerMess.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(), ChatActivity.class));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Integer uid = SessionUtils.get(getActivity(), DataStatic.SESSION.KEY.USER_ID, 0);
                LiveData<CommonResponse<Config>> lvData = mainViewModel.configGet(uid, "fb_message_asset_id");
                lvData.observe(activity, data -> {
                    if(data.getStatus()){
                        Uri uri = Uri.parse("http://m.me/"+data.getData().getValue());
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                });
            }
        });
        linerAmbulance.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ListDoctorActivity.class));
        });
    }

    private void loadDataBooking(){
        Integer uid = SessionUtils.get(getActivity(), DataStatic.SESSION.KEY.USER_ID, 0);
        Integer idStatus = 0;
        if(UserUtils.isAdmin()) idStatus = 3;
        if(UserUtils.isDoctor()) idStatus = 4;
        LiveData<CommonResponse<List<Book>>> lvData = mainViewModel.bookList(uid, idStatus);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                if (UserUtils.isAdmin() || UserUtils.isDoctor()) {
                    adapter2.setDATA(data.getData());
                } else {
                    adapter.setDATA(data.getData());
                }
            }
        });
    }

    private void loadAnalysisByMonth(){
        Integer uid = SessionUtils.get(getActivity(), DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<Analysis>>> lvData = mainViewModel.analysisMonth(uid);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                analysisMonth.clear();
                analysisMonth.addAll(data.getData());
                beMonth.clear();
                chartAnalysisMonth();
            }
        });
    }

    private void loadAnalysisByWeek(){
        Integer uid = SessionUtils.get(getActivity(), DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<Analysis>>> lvData = mainViewModel.analysisWeek(uid);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                analysisWeek.clear();
                analysisWeek.addAll(data.getData());
                beWeek.clear();
                chartAnalysisWeek();
            }
        });
    }

    private void chartAnalysisMonth(){
        List<String> xAxisList = new ArrayList<>();
        beMonth.clear();
        for(int i = 0; i < analysisMonth.size(); i++){
            beMonth.add(new BarEntry(i, analysisMonth.get(i).getTotalPrice()));
            xAxisList.add(analysisMonth.get(i).getTime());
        }
        bdsMonth = new BarDataSet(beMonth,"Thống kê doanh thu theo tháng");

        bdsMonth.setColors(ColorTemplate.MATERIAL_COLORS);
        bdsMonth.setValueTextColor(Color.BLACK);
        bdsMonth.setValueTextSize(14f);

        bdMonth = new BarData(bdsMonth);
        bcMonth.setFitBars(true);
        bcMonth.setData(bdMonth);
        bcMonth.getDescription().setText("");
        bcMonth.getDescription().setTextSize(10f);
        bcMonth.animateY(2000);

        XAxis xAxis = bcMonth.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisList.toArray(new String[0])));
    }


    private void chartAnalysisWeek(){
        List<String> xAxisList = new ArrayList<>();
        beWeek.clear();
        for(int i = 0; i < analysisWeek.size(); i++){
            beWeek.add(new BarEntry(i, analysisWeek.get(i).getTotalPrice()));
            xAxisList.add(analysisWeek.get(i).getTime());
        }
        bdsWeek = new BarDataSet(beWeek,"Thống kê doanh thu theo tuần");

        bdsWeek.setColors(ColorTemplate.MATERIAL_COLORS);
        bdsWeek.setValueTextColor(Color.BLACK);
        bdsWeek.setValueTextSize(14f);

        bdWeek = new BarData(bdsWeek);
        bcWeek.setFitBars(true);
        bcWeek.setData(bdWeek);
        bcWeek.getDescription().setText("");
        bcWeek.getDescription().setTextSize(10f);
        bcWeek.animateY(2000);

        XAxis xAxis = bcWeek.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisList.toArray(new String[0])));
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.one));
        list.add(new Photo(R.drawable.two));
        list.add(new Photo(R.drawable.three));
        list.add(new Photo(R.drawable.dichvu));
        return list;
    }

    private void autoSlide() {
        if (photoList == null || photoList.isEmpty() || vpr == null) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    int curentItem = vpr.getCurrentItem();
                    int toltalItem = photoList.size() - 1;
                    if (curentItem < toltalItem) {
                        curentItem++;
                        vpr.setCurrentItem(curentItem);
                    } else {
                        vpr.setCurrentItem(0);
                    }
                }
            });
            }
        }, 5000, 6000);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataBooking();
        loadAnalysisByMonth();
        loadAnalysisByWeek();

        if(UserUtils.isDoctor()){
            activity.getBottomNavigationView().getMenu().getItem(3).setVisible(false);
        }
    }
}
