package com.example.quanlypet.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.chat.MessageGroupAdapter;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.MessageGroup;
import com.example.quanlypet.model.MessageInfo;
import com.example.quanlypet.ui.activity.ChatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private final static Integer RES_ID = R.layout.fragment_message_group;
    private View view;
    private ChatActivity activity;
    private ListView lvData;
    private MessageGroupAdapter messageGroupAdapter;
    private SearchView svSearchMessage;
    private List<MessageGroup> list;

    public ChatFragment(ChatActivity activity){
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(RES_ID, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        connectView();
        loadMessageGroup();
        actionView();
    }

    private void connectView(){
        this.list = new ArrayList<>();
        this.svSearchMessage = this.view.findViewById(R.id.sv_search_message);
        this.lvData = this.view.findViewById(R.id.lv_data);
        this.messageGroupAdapter = new MessageGroupAdapter(activity, this.list);
        this.lvData.setAdapter(this.messageGroupAdapter);
    }

    private void actionView(){
        svSearchMessage.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                messageGroupAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                messageGroupAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void updateItem(MessageInfo messageInfo){
        for(int i = 0; i < this.list.size(); i++){
            if(this.list.get(i).getId().equals(messageInfo.getIdGroup())){
                this.list.get(i).setContent(messageInfo.getContent());
                this.list.get(i).addTotalUnRead();
                break;
            }
        }
        this.messageGroupAdapter.notifyDataSetChanged();
    }

    private void loadMessageGroup(){
        Integer uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<MessageGroup>>> lvData = activity.getChatViewModel().groupList(uid);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                messageGroupAdapter.notifyDataSetChanged(data.getData());
            }
        });
    }

    public MessageGroupAdapter getMessageGroupAdapter() {
        return messageGroupAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setFragmentActive(this);
        activity.getToolbar().setTitle("Tin nhắn");
    }
}
