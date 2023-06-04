package com.example.quanlypet.adapter.adUse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.model.User;
import com.example.quanlypet.ui.activity.DetailUsersActivity;
import com.example.quanlypet.ui.activity.ListUserActivity;

import java.util.ArrayList;
import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolder> implements Filterable {
    List<User> list;
    List<User> listUsers;
    ListUserActivity activity;

    public ListUserAdapter(ListUserActivity activity, List<User> list) {
        this.activity = activity;
        this.list = list;
        this.listUsers = list;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    list = listUsers;
                }else{
                    ArrayList<User> listdt = new ArrayList<>();
                    for (User object: listUsers){
                        if (object.getFullName().toLowerCase().contains(search.toLowerCase())||
                                object.getEmail().toLowerCase().contains(search.toLowerCase())||
                                object.getPhone().toLowerCase().contains(search.toLowerCase())){
                            listdt.add(object);
                        }
                    }
                    list = listdt;
                }
                // thực hiện quá trình lọc dữ liệu và thêm kết quả vào
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<User>) results.values; // cập nhật danh sách dữ liệu của adapter với kết quả đã được lọc
                notifyDataSetChanged(); // thông báo cho adpater dữ liệu đã được thay đổi
            }
        };
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        User obj = list.get(position);
        holder.tvFullnameUsers.setText(obj.getFullName());
        holder.tvPhoneUsers.setText(obj.getPhone());
        //hiển thị chi tiết người dùng, bằng cách lấy ID
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, DetailUsersActivity.class);
                i.putExtra(DataStatic.INTENT.ID, obj.getId());
                activity.startActivity(i);
            }
        });
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFullnameUsers;
        private TextView tvPhoneUsers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullnameUsers = (TextView) itemView.findViewById(R.id.tv_fullnameUsers);
            tvPhoneUsers = (TextView) itemView.findViewById(R.id.tv_phoneUsers);
        }
    }
}
