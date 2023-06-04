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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.UserUtils;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.ui.activity.AddBillActivity;
import com.example.quanlypet.ui.activity.AddPatientActivity;
import com.example.quanlypet.ui.activity.BookingDetailActivity;
import com.example.quanlypet.viewmodel.AddBookingViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookingAdminAdapter extends RecyclerView.Adapter<BookingAdminAdapter.ViewHolder> {
    private AddBookingViewModel addBookingViewModel;
    List<Book> list = new ArrayList<>();
    MainActivity activity;

    public BookingAdminAdapter(List<Book> list, MainActivity activity) {
        this.activity = activity;
        addBookingViewModel = ViewModelProviders.of((FragmentActivity) this.activity).get(AddBookingViewModel.class);
    }

    public void setDATA(List<Book> list) {
        list.forEach(Book::decrypt);
        this.list = list;
        notifyDataSetChanged();
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_up);

        Book obj = list.get(position);

        holder.nameDoctor.setText(obj.getUserFullName());
        holder.tvService.setText(obj.getServiceName());
        holder.tvTime.setText(obj.getTimeText());
        holder.linnerStatus.setBackgroundColor(Color.YELLOW);
        if (obj.getIdStatus() == 5) {
            if(obj.getIdBill() != null){
                holder.linnerStatus.setBackgroundColor(Color.GREEN);
            }else{
                holder.linnerStatus.setBackgroundColor(Color.YELLOW);
            }
        } else if (obj.getIdStatus() == 6) {
            holder.linnerStatus.setBackgroundColor(Color.RED);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj.getIdStatus() == 3 && UserUtils.isAdmin()) {
                    //Admin Confirm
                    dialogConfirm(obj);
                } else if (obj.getIdStatus() == 4) {
                    if(obj.getIdService() != 4){
                        //Admin Confirm
                        dialogConfirm(obj);
                    }else if(obj.getIdGuest() == null && UserUtils.isDoctor()){
                        //Doctor add guest
                        dialogRequest(obj);
                    }
                } else if (obj.getIdStatus() == 5 && UserUtils.isAdmin()) {
                    //Admin create bill
                    dialogRequest(obj);
                    System.out.println("Admin create bill");
                }else{
                    Intent intent = new Intent(activity, BookingDetailActivity.class);
                    intent.putExtra(DataStatic.INTENT.ID, obj.getId());
                    activity.startActivity(intent);
                }
            }
        });

        holder.itemView.startAnimation(animation);
    }

    public void dialogConfirm(Book book) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_book_confirm_done);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.bg_huy_booking));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;
        Button btnConfirm = dialog.findViewById(R.id.btn_xacnhan);
        Button btnFinish = dialog.findViewById(R.id.btn_hoanthanh);
        Button btnDetail = dialog.findViewById(R.id.btn_detail);
        Button btnClose = dialog.findViewById(R.id.btn_close);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        if ((book.getIdStatus() == 4 && book.getIdService() == 4 && book.getIdGuest() != null)
                || (book.getIdStatus() == 4 && book.getIdService() != 4)) {
            btnCancel.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        }

        if (book.getIdStatus() == 3) {
            //Admin Confirm
            btnCancel.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
            btnFinish.setVisibility(View.GONE);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setIdStatus(4);
                bookSave(dialog, book);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setIdStatus(5);
                bookSave(dialog, book);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book.setIdStatus(6);
                bookSave(dialog, book);
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

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void dialogRequest(Book book){
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_create_bill);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.bg_huy_booking));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;
        Button btnCreateBill = dialog.findViewById(R.id.btn_taobill);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnCreateGuest = dialog.findViewById(R.id.btn_taopatient);
        Button btnDetail = dialog.findViewById(R.id.btn_detail);

        btnCreateGuest.setVisibility(View.GONE);
        btnCreateBill.setVisibility(View.GONE);
        if(book.getIdStatus() == 5){
            btnCreateBill.setVisibility(View.VISIBLE);
        }

        if(book.getIdStatus() == 4 && book.getIdGuest() == null){
            btnCreateGuest.setVisibility(View.VISIBLE);
        }

        btnCreateBill.setOnClickListener(view -> {
            Intent intent = new Intent(activity, AddBillActivity.class);
            intent.putExtra(DataStatic.INTENT.ID_BOOK, book.getId());
            activity.startActivity(intent);
            dialog.dismiss();
        });

        btnCreateGuest.setOnClickListener(view->{
            Intent intent = new Intent(activity, AddPatientActivity.class);
            intent.putExtra(DataStatic.INTENT.ID_ANIMAL, book.getIdAnimal());
            intent.putExtra(DataStatic.INTENT.ID_BOOK, book.getId());
            activity.startActivity(intent);
            dialog.dismiss();
        });

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, BookingDetailActivity.class);
                intent.putExtra(DataStatic.INTENT.ID, book.getId());
                activity.startActivity(intent);
            }
        });
        btnCancel.setOnClickListener(view -> {
            dialog.cancel();
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linnerStatus;
        private TextView titleDoctor;
        private TextView nameDoctor;
        private TextView tvService;
        private TextView tvTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linnerStatus = (LinearLayout) itemView.findViewById(R.id.linner_status);
            titleDoctor = (TextView) itemView.findViewById(R.id.titleDoctor);
            nameDoctor = (TextView) itemView.findViewById(R.id.nameDoctor);
            tvService = (TextView) itemView.findViewById(R.id.tv_service);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
