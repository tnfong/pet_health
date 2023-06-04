package com.example.quanlypet.adapter.spinner;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlypet.R;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpinnerDoctor extends BaseAdapter {
    private Activity activity;
    private CircleImageView imgAnimal;
    private TextView tvNameAnimal;
    private TextView tvTypeAnimal;
    List<User> list;

    public SpinnerDoctor(Activity activity) {
        this.activity = activity;
    }

    public void setDATA(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        User user = list.get(position);
        return user;
    }

    @Override
    public long getItemId(int position) {
        User user = list.get(position);
        return user.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), R.layout.spinner_doctor, null);
        } else {
            view = convertView;
        }
        User doctor = list.get(position);

        imgAnimal = (CircleImageView) view.findViewById(R.id.img_animal);
        tvNameAnimal = (TextView) view.findViewById(R.id.tv_nameAnimal);
        tvTypeAnimal = (TextView) view.findViewById(R.id.tv_typeAnimal);
        tvNameAnimal.setText("Tên: "+ doctor.getFullName());
        tvTypeAnimal.setText("Chuyên ngành: "+ doctor.getSpecialize());
        ImageUtils.loadUrl(activity, imgAnimal, doctor.getAvatarUrl());

        return view;

    }


}
