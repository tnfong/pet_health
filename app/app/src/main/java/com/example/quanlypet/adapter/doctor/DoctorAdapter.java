package com.example.quanlypet.adapter.doctor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.model.User;
import com.example.quanlypet.ui.activity.AddDoctorActivity;
import com.example.quanlypet.ui.activity.DoctorInfoActivity;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> implements Filterable {
    private Activity context;
    private List<User> list = new ArrayList<>();
    private List<User> listDoctor = new ArrayList<>();

    public void setDataDocter(List<User> list){
        list.forEach(User::decrypt);
        this.list=list;
        this.listDoctor =list;
        notifyDataSetChanged();
    }

    public DoctorAdapter(Activity context) {
        this.context = context;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    list = listDoctor;
                }else{
                    ArrayList<User> listdt = new ArrayList<>();
                    for (User object: listDoctor){
                        if (object.getFullName().toLowerCase().contains(search.toLowerCase())||
                                object.getEmail().toLowerCase().contains(search.toLowerCase())||
                        object.getPhone().toLowerCase().contains(search.toLowerCase())||
                        object.getSpecialize().toLowerCase().contains(search.toLowerCase())){
                            listdt.add(object);
                        }
                    }
                    list = listdt;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_docter,parent,false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_up);
        holder.itemView.startAnimation(animation);
        holder.showInfo(list.get(position));
        holder.actionView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvEmail;
        private TextView tvGender;
        private ImageView imgInformation;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            connectView(itemView);
        }

        public void connectView(View view){
            imgAvatar = view.findViewById(R.id.img_doctor);
            imgInformation = view.findViewById(R.id.img_information);
            tvName = view.findViewById(R.id.tv_name);
            tvEmail = view.findViewById(R.id.tv_email);
            tvGender = view.findViewById(R.id.tv_gender);
        }

        public void showInfo(User user){
            tvName.setText(user.getFullName());
            tvEmail.setText(user.getEmail());
            ImageUtils.loadUrl(context, imgAvatar, user.getAvatarUrl());
            if(user.getGender() == 1){
                tvGender.setText("Nam");
            }else{
                tvGender.setText("Nữ");
            }
        }

        public void actionView(User user){
            imgInformation.setOnClickListener(v->{
                Intent intent = new Intent(context, DoctorInfoActivity.class);
                intent.putExtra(DataStatic.INTENT.ID, user.getId());
                context.startActivity(intent);
            });

            if(UserUtils.isAdmin()){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AddDoctorActivity.class);
                        intent.putExtra(DataStatic.INTENT.ID, user.getId());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

}
