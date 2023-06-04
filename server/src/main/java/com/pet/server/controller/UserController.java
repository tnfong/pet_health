package com.pet.server.controller;

import com.pet.server.common.utils.UrlUtils;
import com.pet.server.dto.UserDTO;
import com.pet.server.entity.User;
import com.pet.server.service.IRoleService;
import com.pet.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping({"", "/list"})
    public String goList(Model model, UserDTO search){
        model.addAttribute("search", search);
        model.addAttribute("roleList", roleService.getAll());
        model.addAttribute("data", userService.getDataPage(search));
        return "admin/component/user/list";
    }

    @GetMapping("/add")
    public String goAdd(Model model){
        model.addAttribute("userForm", new UserDTO());
        model.addAttribute("roleList", roleService.getAll());
        return "admin/component/user/add";
    }

    @PostMapping("/add")
    public String doAdd(HttpServletRequest request, RedirectAttributes attr, @ModelAttribute("userForm") UserDTO userForm){
        User userInfo = userService.save(userForm);
        attr.addFlashAttribute("mess", (userInfo == null) ? "Tạo mới thất bại" : "Tạo mới thành công");
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }

    @GetMapping("/update/{id}")
    public String goUpdate(Model model, HttpServletRequest request, RedirectAttributes attr, @PathVariable("id") Integer id){
        User userInfo = userService.getById(id);
        if(userInfo == null){
            return UrlUtils.getPreviousPageByRequest(request).orElse("/");
        }
        model.addAttribute("userForm", userInfo);
        model.addAttribute("roleList", roleService.getAll());
        return "admin/component/user/update";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(HttpServletRequest request, RedirectAttributes attr, @PathVariable("id") Integer id, @ModelAttribute("form") UserDTO userForm){
        userForm.setId(id);
        User user = userService.save(userForm);
        attr.addFlashAttribute("mess", (user == null) ? "Update fail" : "Update successful");
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }

    @GetMapping("/delete/{id}")
    public String doDelete(HttpServletRequest request, RedirectAttributes attr, @PathVariable("id") Integer id){
        Boolean check = userService.delete(id);
        attr.addFlashAttribute("mess", check ? "Update successful" : "Update fail");
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }
}
