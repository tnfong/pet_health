package com.example.quanlypet.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.chat.MessageInfoAdapter;
import com.example.quanlypet.common.DataStatic;
import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.SessionUtils;
import com.example.quanlypet.model.MessageGroup;
import com.example.quanlypet.model.MessageInfo;
import com.example.quanlypet.ui.activity.ChatActivity;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    private final static Integer RES_ID = R.layout.fragment_message;
    private View view;
    private ChatActivity activity;
    private Integer idGroup;
    private RecyclerView rvData;
    private MessageInfoAdapter messageInfoAdapter;
    private List<MessageInfo> list;
    private ImageView ivSend;
    private EditText etContent;

    public MessageFragment(ChatActivity activity, Integer id){
        this.activity = activity;
        this.idGroup = id;
        this.activity.setFragmentActive(this);
    }

    public Integer getIdGroup() {
        return idGroup;
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
        actionView();
        loadMessageList();
    }

    public void pushMessage(MessageInfo messageInfo){
        list.add(messageInfo);
        messageInfoAdapter.notifyDataSetChanged();
        rvData.scrollToPosition(list.size() - 1);
    }

    private void connectView(){
        list = new ArrayList<>();
        rvData = this.view.findViewById(R.id.rv_data);
        messageInfoAdapter = new MessageInfoAdapter(activity, list);
        rvData.setAdapter(messageInfoAdapter);
        rvData.setLayoutManager(new LinearLayoutManager(activity));
        etContent = this.view.findViewById(R.id.et_content);
        ivSend = this.view.findViewById(R.id.iv_send);
    }

    private void actionView(){
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageInfo info = new MessageInfo();
                info.setIdUser(SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0));
                info.setIdGroup(idGroup);
                info.setContent(etContent.getText().toString());
                activity.sendMessage(info);
                etContent.setText("");
            }
        });
    }

    private void loadMessageList(){
        Integer uid = SessionUtils.get(activity, DataStatic.SESSION.KEY.USER_ID, 0);
        LiveData<CommonResponse<List<MessageInfo>>> lvData = activity.getChatViewModel().messageList(uid, idGroup);
        lvData.observe(activity, data -> {
            if(data.getStatus()){
                list.clear();
                list.addAll(data.getData());
                messageInfoAdapter.notifyDataSetChanged();
                rvData.scrollToPosition(list.size() - 1);
            }
        });
    }
}
