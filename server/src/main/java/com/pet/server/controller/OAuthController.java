package com.pet.server.controller;

import com.pet.server.common.utils.DataStatic;
import com.pet.server.common.utils.UrlUtils;
import com.pet.server.dto.UserDTO;
import com.pet.server.entity.User;
import com.pet.server.service.IOauthService;
import com.pet.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private IOauthService oauthService;

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String goLogin(Model model){
        model.addAttribute("loginForm", new UserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpSession session, HttpServletRequest request, RedirectAttributes attr, @ModelAttribute("loginForm") UserDTO userDTO){
        String id = oauthService.login(userDTO);
        if(id != null){
            User user = userService.getById(Integer.valueOf(id));
            if(user.getIdRole().equals(1)){
                session.setAttribute(DataStatic.SESSION.KEY.LOGIN_ID, id);
                session.setAttribute(DataStatic.SESSION.KEY.SESSION_LOGIN, user);
                return "redirect:/";
            }else{
                attr.addFlashAttribute("mess", "Tài khoản không có quyền hạn");
                return UrlUtils.getPreviousPageByRequest(request).orElse("/");
            }
        }else{
            attr.addFlashAttribute("mess", "Tài khoản không hợp lệ");
            return UrlUtils.getPreviousPageByRequest(request).orElse("/");
        }
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession session){
        session.removeAttribute(DataStatic.SESSION.KEY.LOGIN_ID);
        session.removeAttribute(DataStatic.SESSION.KEY.SESSION_LOGIN);
        return "redirect:/oauth/login";
    }
}
