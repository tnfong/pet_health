package com.example.quanlypet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.model.MessageGroup;
import com.example.quanlypet.model.MessageInfo;
import com.example.quanlypet.repository.MessageGroupRepository;
import com.example.quanlypet.repository.MessageInfoRepository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    private MessageInfoRepository messageInfoRepository;
    private MessageGroupRepository messageGroupRepository;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        this.messageInfoRepository = new MessageInfoRepository();
        this.messageGroupRepository = new MessageGroupRepository();
    }

    public LiveData<CommonResponse<List<MessageGroup>>> groupList(Integer uid){
        return this.messageGroupRepository.list(uid);
    }

    public LiveData<CommonResponse<Integer>> groupCreate(Integer uid, Integer idUser){
        return this.messageGroupRepository.create(uid, idUser);
    }

    public LiveData<CommonResponse<List<MessageInfo>>> messageList(Integer uid, Integer idGroup){
        return this.messageInfoRepository.list(uid, idGroup);
    }
}
