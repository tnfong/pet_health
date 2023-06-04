package com.pet.server.service.impl;

import com.pet.server.common.utils.ListUtils;
import com.pet.server.entity.MessageGroup;
import com.pet.server.entity.MessageMember;
import com.pet.server.entity.User;
import com.pet.server.repository.MessageGroupRepository;
import com.pet.server.repository.MessageInfoRepository;
import com.pet.server.repository.MessageMemberRepository;
import com.pet.server.service.IMessageGroupService;
import com.pet.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageGroupService implements IMessageGroupService {

    @Autowired
    private MessageGroupRepository messageGroupRepository;

    @Autowired
    private MessageInfoRepository messageInfoRepository;

    @Autowired
    private MessageMemberRepository messageMemberRepository;

    @Autowired
    private IUserService userService;

    @Override
    public List<Map<String, Object>> list(Integer uid) {
        List<Map<String, Object>> lst = messageGroupRepository.getMyList(uid);
        List<Map<String, Object>> lstMessNotRead = messageInfoRepository.countMessageNotReadByGroup(uid);
        List<Map<String, Object>> result = new ArrayList<>();
        if(lstMessNotRead.size() > 0){
            Map<String, List<Map<String, Object>>> mapMessNotRead = ListUtils.toMapList(lstMessNotRead, "idGroup", true);
            for(int i = 0; i < lst.size(); i++){
                Integer idGroup = (Integer) lst.get(i).get("id");
                Map<String, Object> item = new HashMap<>(lst.get(i));
                item.replace("totalUnRead", mapMessNotRead.get(idGroup+"") == null ? 0 : mapMessNotRead.get(idGroup+"").get(0).get("total"));
                result.add(item);
            }
        }else{
            result.addAll(lst);
        }
        return result;
    }

    @Override
    public Integer create(Integer uid, Integer idUser) {
        List<Map<String, Object>> lst = messageMemberRepository.checkGroupByUser(uid, idUser);
        Integer idGroup = 0;
        if(lst.size() > 0){
            idGroup = (Integer) lst.get(0).get("idGroup");
        }else{
            MessageGroup gms = new MessageGroup();
            messageGroupRepository.save(gms);
            idGroup = gms.getId();
            messageMemberRepository.save(new MessageMember(idGroup, uid));
            messageMemberRepository.save(new MessageMember(idGroup, idUser));
        }
        return idGroup;
    }
}
