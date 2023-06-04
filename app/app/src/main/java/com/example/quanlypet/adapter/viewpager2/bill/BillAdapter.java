package com.example.quanlypet.adapter.viewpager2.bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.common.utils.DateUtils;
import com.example.quanlypet.model.Bill;
import com.example.quanlypet.model.User;
import com.example.quanlypet.ui.dialog.BillDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private MainActivity activity;
    private List<Bill> arrayList;
    private List<Bill> listBill;
    public BillAdapter(MainActivity activity, List<Bill> list) {
        this.activity = activity;
        this.arrayList = list;
        this.listBill = list;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
//        holder.actionView(listBill.get(position));
        holder.showData(listBill.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList == null?0:arrayList.size();
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPriceBill;
        private TextView tvDate;
        private TextView tvNameUsers;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            connectView();
        }

        private void connectView(){
            tvNameUsers = itemView.findViewById(R.id.tv_nameUsers);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvPriceBill = itemView.findViewById(R.id.tv_priceBill);
        }

        private void actionView(Bill bill){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new BillDialog(activity, bill).show();
                }
            });
        }

        public void showData(Bill bill){
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.slide_up);
            tvNameUsers.setText(bill.getUserFullName());
            tvDate.setText(DateUtils.format(bill.getCreatedDate(), "yyyy/MM/dd HH:mm"));
            tvPriceBill.setText(NumberFormat.getInstance(Locale.US).format(bill.getPrice()));
            itemView.startAnimation(animation);
        }
    }
}