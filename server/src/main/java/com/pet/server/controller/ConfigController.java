package com.pet.server.controller;

import com.pet.server.common.utils.UrlUtils;
import com.pet.server.dto.ConfigDTO;
import com.pet.server.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private IConfigService configService;

    @GetMapping
    public String goConfig(Model model){
        model.addAttribute("configList", configService.getList());
        return "admin/component/config/list";
    }

    @PostMapping
    public String doConfig(HttpServletRequest request, ConfigDTO configDTO){
        configService.update(configDTO);
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }

}
