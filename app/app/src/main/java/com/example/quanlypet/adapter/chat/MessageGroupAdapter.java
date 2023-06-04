package com.example.quanlypet.adapter.chat;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.CryptoUtils;
import com.example.quanlypet.common.utils.DateUtils;
import com.example.quanlypet.common.utils.ImageUtils;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.MessageGroup;
import com.example.quanlypet.ui.activity.ChatActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageGroupAdapter extends ArrayAdapter<MessageGroup>  implements Filterable {

    private static final Integer RES_ID = R.layout.item_message_group;
    private ChatActivity activity;
    private List<MessageGroup> messageGroup, listFilter;
    private ViewHolder viewHolder;

    public MessageGroupAdapter(@NonNull ChatActivity activity, @NonNull List<MessageGroup> list) {
        super(activity, RES_ID, list);
        this.activity = activity;
        this.messageGroup = list;
        this.listFilter = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        convertView = inflater.inflate(RES_ID, null);
        if(position < listFilter.size()){
            viewHolder = new ViewHolder(convertView);
            viewHolder.showData(listFilter.get(position));
            viewHolder.actionView(listFilter.get(position));
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    listFilter = messageGroup;
                }else{
                    listFilter.clear();
                    for (MessageGroup item : messageGroup){
                        if (item.getName().toLowerCase().contains(search.toLowerCase())){
                            listFilter.add(item);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFilter = (List<MessageGroup>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return listFilter.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<MessageGroup> list) {
        super.notifyDataSetChanged();
        this.messageGroup.clear();
        this.messageGroup.addAll(list);
        this.listFilter.clear();
        this.listFilter.addAll(list);
    }

    private class ViewHolder {
        private View view;
        private CircleImageView ivAvatar;
        private TextView tvName, tvContent, tvTime, tvCount;

        public ViewHolder(View view){
            this.view = view;
            connectView();
        }

        private void connectView(){
            this.ivAvatar = this.view.findViewById(R.id.iv_avatar);
            this.tvName = this.view.findViewById(R.id.tv_name);
            this.tvContent = this.view.findViewById(R.id.tv_content);
            this.tvTime = this.view.findViewById(R.id.tv_time);
            this.tvCount = this.view.findViewById(R.id.tv_count);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void showData(MessageGroup messageGroup){
            Integer uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0);
            ImageUtils.loadUrl(activity, this.ivAvatar, messageGroup.getAvatarUrl());
            try {
                this.tvName.setText(CryptoUtils.AES.decrypt(messageGroup.getName()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(messageGroup.getContent() != null){
                String content = "";
                if(uid.equals(messageGroup.getIdFrom())){
                    content = String.format("[Bạn]: %s", messageGroup.getContent());
                }else{
                    content = String.format("%s", messageGroup.getContent());
                }
                this.tvContent.setVisibility(View.VISIBLE);
                this.tvContent.setText(content);
            }
            if(messageGroup.getTotalUnRead() != null && messageGroup.getTotalUnRead() > 0){
                this.tvCount.setVisibility(View.VISIBLE);
                this.tvCount.setText(String.format("+%d", messageGroup.getTotalUnRead()));
            }
            if(messageGroup.getCreatedDate() != null){
                this.tvTime.setVisibility(View.VISIBLE);
                this.tvTime.setText(DateUtils.format(messageGroup.getCreatedDate(), "yyyy/MM/dd HH:mm:ss"));
            }
        }

        public void actionView(MessageGroup messageGroup){
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(messageGroup.getId() == null){
                        Integer uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0);
                        LiveData<CommonResponse<Integer>> lvData = activity.getChatViewModel().groupCreate(uid, messageGroup.getIdUser());
                        lvData.observe(activity, data -> {
                            if(data.getStatus()){
                                activity.getMessageFragment(data.getData(), messageGroup.getName());
                            }
                        });
                    }else{
                        activity.getMessageFragment(messageGroup.getId(), messageGroup.getName());
                    }
                }
            });
        }
    }
}
