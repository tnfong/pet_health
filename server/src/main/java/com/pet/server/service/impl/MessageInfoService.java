package com.pet.server.service.impl;

import com.pet.server.dto.MessageInfoDTO;
import com.pet.server.entity.MessageInfo;
import com.pet.server.entity.MessageMember;
import com.pet.server.repository.MessageGroupRepository;
import com.pet.server.repository.MessageInfoRepository;
import com.pet.server.repository.MessageMemberRepository;
import com.pet.server.service.IMessageGroupService;
import com.pet.server.service.IMessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MessageInfoService implements IMessageInfoService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageGroupRepository memberGroupRepository;

    @Autowired
    private MessageInfoRepository messageInfoRepository;

    @Autowired
    private MessageMemberRepository messageMemberRepository;

    @Autowired
    private UserService userService;

    public MessageInfo create(MessageInfoDTO messageInfoDTO){
        MessageInfo message = new MessageInfo(messageInfoDTO);
        messageInfoRepository.save(message);
        List<MessageMember> members = messageMemberRepository.findByIdGroup(message.getIdGroup());
        members.forEach((el) -> {
            simpMessagingTemplate.convertAndSendToUser(el.getIdUser()+"", "/queue/messages", message);
        });
        return message;
    }

    @Override
    public List<Map<String, Object>> list(Integer uid, Integer idGroup) {
        if(idGroup == null) return new ArrayList<>();
        messageInfoRepository.updateReadMessage(idGroup, uid);
        return messageInfoRepository.getByGroup(idGroup);
    }

    @Override
    public boolean updateReadMessage(Integer uid, Integer idGroup) {
        messageInfoRepository.updateReadMessage(idGroup, uid);
        return true;
    }

}
