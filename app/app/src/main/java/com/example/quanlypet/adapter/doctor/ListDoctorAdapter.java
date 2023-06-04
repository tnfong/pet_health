package com.example.quanlypet.adapter.doctor;

import static com.example.quanlypet.R.drawable.bg_dialog_call;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListDoctorAdapter extends RecyclerView.Adapter<ListDoctorAdapter.DSDocterViewHolder>{
    private Context context;
    private ArrayList<User> listDS;

    public void setDataDanhSach(ArrayList<User> listDS){
        this.listDS=listDS;
        notifyDataSetChanged();
    }

    public ListDoctorAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DSDocterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emergency,parent,false);
        return new ListDoctorAdapter.DSDocterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSDocterViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        User doctor = listDS.get(position);
        if(doctor ==null)
            return;
        holder.tvName.setText(doctor.getFullName());
        holder.imgDoctor.setTag(doctor.getAvatarUrl());

        holder.idRelativeLayout.setOnClickListener(v->{
            Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.dialog_call_phone);
            Button btnCall = dialog.findViewById(R.id.btn_call);
            Button btnCancel = dialog.findViewById(R.id.btn_cancel);

            Window window = dialog.getWindow();
            dialog.getWindow().setBackgroundDrawableResource(bg_dialog_call);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            window.setAttributes(windowAttributes);
            windowAttributes.gravity = Gravity.BOTTOM;
            btnCall.setText(doctor.getPhone());
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel: "+ doctor.getPhone()));
                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity( sharingIntent);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return listDS==null?0:listDS.size();
    }

    public class DSDocterViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgDoctor;
        private TextView tvName;
        private ImageView imgInformation;
        private RelativeLayout idRelativeLayout;


        public DSDocterViewHolder(@NonNull View itemView) {
            super(itemView);
            idRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.id_relativeLayout);

            imgDoctor = (CircleImageView) itemView.findViewById(R.id.img_doctor);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            imgInformation = (ImageView) itemView.findViewById(R.id.img_information);

        }
    }
}
