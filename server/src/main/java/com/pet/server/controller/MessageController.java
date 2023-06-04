package com.pet.server.controller;

import com.pet.server.common.utils.DataStatic;
import com.pet.server.dto.MessageGroupDTO;
import com.pet.server.service.IMessageGroupService;
import com.pet.server.service.IMessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageGroupService messageGroupService;

    @Autowired
    private IMessageInfoService messageInfoService;

    @GetMapping({"", "/index"})
    public String goIndex(Model model, @SessionAttribute(DataStatic.SESSION.KEY.LOGIN_ID) Integer uid, MessageGroupDTO groupMessage){

        if(groupMessage.getId() == null && groupMessage.getIdUser() != null){
            Integer idGroup = messageGroupService.create(uid, groupMessage.getIdUser());
            return "redirect:/message?id="+idGroup;
        }
        model.addAttribute("messageData", messageInfoService.list(uid, groupMessage.getId()));
        model.addAttribute("groupData", messageGroupService.list(uid));
        model.addAttribute("idGroup", groupMessage.getId());
        return "admin/component/message";
    }
}
