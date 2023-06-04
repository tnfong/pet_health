package com.pet.server.controller;

import com.pet.server.common.utils.UrlUtils;
import com.pet.server.dto.ServiceDTO;
import com.pet.server.entity.Service;
import com.pet.server.service.IServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/service")
public class ServiceController {
    
    @Autowired
    private IServiceService serviceService;
    
    @GetMapping({"", "/list"})
    public String goList(Model model, ServiceDTO search){
        model.addAttribute("search", search);
        model.addAttribute("data", serviceService.getDataPage(search));
        return "admin/component/service/list";
    }

    @GetMapping("/add")
    public String goAdd(Model model){
        model.addAttribute("serviceForm", new ServiceDTO());
        return "admin/component/service/add";
    }

    @PostMapping("/add")
    public String doAdd(HttpServletRequest request, RedirectAttributes attr, @ModelAttribute("serviceForm") ServiceDTO serviceForm){
        Service service = serviceService.save(serviceForm);
        attr.addFlashAttribute("mess", (service == null) ? "Tạo mới thất bại" : "Tạo mới thành công");
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }

    @GetMapping("/update/{id}")
    public String goUpdate(Model model, HttpServletRequest request, @PathVariable("id") Integer id){
        Service service = serviceService.getById(id);
        if(service == null){
            return UrlUtils.getPreviousPageByRequest(request).orElse("/");
        }
        model.addAttribute("serviceForm", service);
        return "admin/component/service/update";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(HttpServletRequest request, RedirectAttributes attr, @PathVariable("id") Integer id, @ModelAttribute("serviceForm") ServiceDTO serviceForm){
        Service service = serviceService.save(serviceForm);
        attr.addFlashAttribute("mess", (service == null) ? "Update fail" : "Update successful");
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }

}
