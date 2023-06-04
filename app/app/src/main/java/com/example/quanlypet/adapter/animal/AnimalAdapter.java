package com.example.quanlypet.adapter.animal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.model.Animal;
import com.example.quanlypet.ui.activity.PatientActivity;
import com.example.quanlypet.ui.dialog.QrCodeDialog;

import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> implements Filterable {
    private Activity context;
    private Callback callback;
    private ArrayList<Animal> arrayList;
    private ArrayList<Animal> listAnimal;


    public AnimalAdapter(Activity context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }
    public void setData(ArrayList<Animal> arrayList){
        this.arrayList = arrayList;
        this.listAnimal = arrayList;
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    arrayList = listAnimal;
                }else{
                    ArrayList<Animal> listdt = new ArrayList<>();
                    for (Animal object: listAnimal){
                        if (object.getName().toLowerCase().contains(search.toLowerCase())||
                                object.getSpecies().toLowerCase().contains(search.toLowerCase())){
                            listdt.add(object);
                        }
                    }
                    arrayList = listdt;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<Animal>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        Animal animal = arrayList.get(position);
        if (animal == null)
            return;
        holder.tvNameAnimal.setText(animal.getName());
        ImageUtils.loadUrl(context, holder.imgAnhItem, animal.getAvatarUrl());

        holder.tvAge.setText(String.valueOf(animal.getAge()));
        holder.tvLoai.setText(animal.getSpecies());

        holder.relyAnimal.setOnClickListener(v ->{
            callback.Update(animal);
        });

        holder.imgInformation.setOnClickListener(v ->{
            Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.dialog_benh_an);
            dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.bg_huy_booking));
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            window.setAttributes(windowAttributes);
            windowAttributes.gravity = Gravity.BOTTOM;
            Button btnQrCode = dialog.findViewById(R.id.btn_qrcode);
            Button btnXemBenhAn = dialog.findViewById(R.id.btn_xemBenhAn);
            Button btnCancel  = dialog.findViewById(R.id.btn_cancel);

            btnXemBenhAn.setOnClickListener(view->{
                Intent intent = new Intent(v.getContext(), PatientActivity.class);
                intent.putExtra(DataStatic.INTENT.ID_ANIMAL, animal.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });

            btnCancel.setOnClickListener(view->{
                dialog.dismiss();
            });

            btnQrCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new QrCodeDialog(context, animal.getName(), animal.getId()).show();
                }
            });
            dialog.show();
        });
        holder.itemView.startAnimation(animation);
    }
    @Override
    public int getItemCount() {
        return arrayList == null ? 0: arrayList.size();
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relyAnimal;
        private TextView tvNameAnimal;
        private ImageView imgAnhItem;
        private TextView tvAge;
        private TextView tvLoai;
        private ImageView imgInformation;
        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            relyAnimal = itemView.findViewById(R.id.rely_animal);
            tvNameAnimal = itemView.findViewById(R.id.tv_nameAnimal);
            imgAnhItem = itemView.findViewById(R.id.img_anh_item);
            tvAge = itemView.findViewById(R.id.tv_age);
            tvLoai = itemView.findViewById(R.id.tv_loai);
            imgInformation = itemView.findViewById(R.id.img_information);
        }
    }
    public interface Callback{
        void Update(Animal object);
    }
}
