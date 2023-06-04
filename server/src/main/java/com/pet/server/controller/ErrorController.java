package com.pet.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/401")
    public String goError401(){
        return "error/401";
    }

    @GetMapping("/404")
    public String goError404(){
        return "error/404";
    }
}
