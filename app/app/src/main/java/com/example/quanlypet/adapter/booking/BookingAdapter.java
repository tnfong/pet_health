package com.example.quanlypet.adapter.booking;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.ui.activity.BookingDetailActivity;
import com.example.quanlypet.viewmodel.AddBookingViewModel;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private AddBookingViewModel addBookingViewModel;
    List<Book> list;
    MainActivity activity;

    public BookingAdapter(MainActivity mContext) {
        this.activity = mContext;
        addBookingViewModel = ViewModelProviders.of((FragmentActivity) this.activity).get(AddBookingViewModel.class);
    }

    public void setDATA(List<Book> list) {
        list.forEach(Book::decrypt);
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.showData(list.get(position));
    }

    private void bookSave(Dialog dialog, Book book){
        LiveData<CommonResponse<Book>> lvData = addBookingViewModel.save(book);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                list.remove(book);
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });
    }

    public void showDiaLogHuy(Book book) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_cancel_book);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.bg_huy_booking));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;
        Button btn_huy = dialog.findViewById(R.id.btn_huylich);
        Button btn_cancle = dialog.findViewById(R.id.btn_cancel);
        Button btnDetail = dialog.findViewById(R.id.btn_detail);

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setIdStatus(6);
                bookSave(dialog, book);
            }
        });

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, BookingDetailActivity.class);
                intent.putExtra(DataStatic.INTENT.ID, book.getId());
                activity.startActivity(intent);
            }
        });

        dialog.show();

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linnerStatus;
        private TextView titleDoctor;
        private TextView nameDoctor;
        private TextView tvAddress;
        private TextView tvNamePet;
        private TextView tvService;
        private TextView tvTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleDoctor = (TextView) itemView.findViewById(R.id.titleDoctor);
            nameDoctor = (TextView) itemView.findViewById(R.id.nameDoctor);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvNamePet = (TextView) itemView.findViewById(R.id.tv_namePet);
            tvService = (TextView) itemView.findViewById(R.id.tv_service);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            linnerStatus = itemView.findViewById(R.id.linner_status);
        }

        public void showData(Book book){
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            tvTime.setText(book.getTimeText());
            tvService.setText(book.getServiceName());
            nameDoctor.setText(book.getDoctorFullName());
            tvAddress.setText(book.getAddress());
            tvNamePet.setText(book.getAnimalName());

            if (book.getIdStatus() == 3) {
                linnerStatus.setBackgroundColor(Color.YELLOW);
            } else if (book.getIdStatus() == 4) {
                linnerStatus.setBackgroundColor(Color.GRAY);
            } else if (book.getIdStatus() == 5) {
                linnerStatus.setBackgroundColor(Color.GREEN);
            } else if (book.getIdStatus() == 6) {
                linnerStatus.setBackgroundColor(Color.BLUE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(book.getIdStatus() == 3){
                        showDiaLogHuy(book);
                    }else{
                        Intent intent = new Intent(activity, BookingDetailActivity.class);
                        intent.putExtra(DataStatic.INTENT.ID, book.getId());
                        activity.startActivity(intent);
                    }
                }
            });
            itemView.startAnimation(animation);
        }
    }
}
