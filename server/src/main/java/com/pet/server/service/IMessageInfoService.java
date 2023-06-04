package com.pet.server.service;

import com.pet.server.dto.MessageInfoDTO;
import com.pet.server.entity.MessageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IMessageInfoService {
    MessageInfo create(MessageInfoDTO messageInfoDTO);
    List<Map<String, Object>> list(Integer uid, Integer idGroup);
    boolean updateReadMessage(Integer uid, Integer idGroup);
}
