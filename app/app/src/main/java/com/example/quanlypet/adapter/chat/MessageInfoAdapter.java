package com.example.quanlypet.adapter.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.utils.DateUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.MessageInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageInfoAdapter extends RecyclerView.Adapter<MessageInfoAdapter.ViewHolder> {
    private static final Integer RES_ID_LEFT = R.layout.item_message_info_left;
    private static final Integer RES_ID_RIGHT = R.layout.item_message_info_right;
    private Activity activity;
    private List<MessageInfo> list;

    public MessageInfoAdapter(@NonNull Activity activity, @NonNull List<MessageInfo> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        Integer uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0);
        if(uid.equals(list.get(position).getIdUser())){
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view;
        if(viewType == 0){
            view = inflater.inflate(RES_ID_LEFT, parent, false);
        }else{
            view = inflater.inflate(RES_ID_RIGHT, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.showData(list.get(position));
//        holder.actionView(travel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tvContent;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            connectView();
        }

        private void connectView(){
            this.tvContent = this.view.findViewById(R.id.tv_content);
        }

        public void showData(MessageInfo messageInfo){
            this.tvContent.setText(messageInfo.getContent());
        }
    }
}
