package com.example.quanlypet.adapter.patient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.model.Guest;

import java.text.SimpleDateFormat;
import java.util.List;


public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private Activity context;
    private List<Guest> list;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PatientAdapter(Activity context, List<Guest> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient,parent,false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Guest guest = list.get(position);
        holder.showData(guest);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout idRelativeLayout;
        private ImageView imgDocter;
        private TextView tvDateCreate;
        private TextView tvDateUpdate;
        private TextView tvSysptems;
        private TextView tvDiagnostic;
        private TextView tvInstructions;


        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            idRelativeLayout = itemView.findViewById(R.id.id_relativeLayout);
            imgDocter = itemView.findViewById(R.id.img_doctor);

            tvDateCreate = itemView.findViewById(R.id.tv_dateCreate);
            tvDateUpdate = itemView.findViewById(R.id.tv_dateUpdate);
            tvSysptems = itemView.findViewById(R.id.tv_sysptems);
            tvDiagnostic = itemView.findViewById(R.id.tv_diagnostic);
            tvInstructions = itemView.findViewById(R.id.tv_instructions);
        }

        public void showData(Guest guest){
            tvDateCreate.setText(sdf.format(guest.getCreatedDate()));
            tvDateUpdate.setText(sdf.format(guest.getUpdatedDate()));
            tvSysptems.setText(guest.getSymptoms());
            tvInstructions.setText(guest.getInstructions());
            tvDiagnostic.setText(guest.getDiagnostic());
            ImageUtils.loadUrl(context, imgDocter, guest.getDoctorUrl());
        }
    }
}
