package com.pet.server.controller;

import com.google.gson.Gson;
import com.pet.server.dto.MessageInfoDTO;
import com.pet.server.entity.MessageInfo;
import com.pet.server.service.IMessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @Autowired
    private IMessageInfoService messageService;

    @MessageMapping("/chat")
    public void chat(@Payload String message){
        MessageInfoDTO m = new Gson().fromJson(message, MessageInfoDTO.class);
        messageService.create(m);
    }
}
