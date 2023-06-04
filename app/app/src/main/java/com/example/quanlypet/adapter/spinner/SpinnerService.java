package com.example.quanlypet.adapter.spinner;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlypet.R;
import com.example.quanlypet.model.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SpinnerService extends BaseAdapter {

    private static final Integer RES_ID = R.layout.item_spinner_service;
    private List<Service> services;
    private Activity activity;
    private ViewHolder viewHolder;

    public SpinnerService(@NonNull Activity activity, @NonNull List<Service> services) {
        this.services = services;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return services.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), RES_ID, null);
        } else {
            view = convertView;
        }

        viewHolder = new ViewHolder(view);
        viewHolder.showData(services.get(position));

        return view;

    }

    private class ViewHolder{
        private View v;
        private TextView tvName, tvPrice;

        public ViewHolder(View view){
            this.v = view;
            this.tvName = this.v.findViewById(R.id.tv_name);
            this.tvPrice = this.v.findViewById(R.id.tv_price);
        }

        public void showData(Service service){
            this.tvName.setText(service.getName());
            this.tvPrice.setText(NumberFormat.getInstance(Locale.US).format(service.getPrice())+"đ");
        }
    }
}
