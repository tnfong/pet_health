package com.example.quanlypet.adapter.spinner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlypet.R;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.model.Animal;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpinnerAnimal extends BaseAdapter {
    private Activity activity;
    private CircleImageView imgAnimal;
    private TextView tvNameAnimal;
    private TextView tvTypeAnimal;
    List<Animal> list;

    public SpinnerAnimal(Activity activity){
        this.activity = activity;
    }

    public void setData(List<Animal> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        Animal obj = list.get(position);
        return obj;
    }

    @Override
    public long getItemId(int position) {
        Animal obj = list.get(position);
        return obj.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), R.layout.spinner_animal, null);
        } else {
            view = convertView;
        }

        imgAnimal = (CircleImageView) view.findViewById(R.id.img_animal);
        tvNameAnimal = (TextView) view.findViewById(R.id.tv_nameAnimal);
        tvTypeAnimal = (TextView) view.findViewById(R.id.tv_typeAnimal);
        Animal animalObj = list.get(position);
        tvNameAnimal.setText("Tên: "+animalObj.getName());
        tvTypeAnimal.setText("Giống: "+animalObj.getSpecies());
        ImageUtils.loadUrl(activity, imgAnimal, animalObj.getAvatarUrl());

        return view;
    }
}
