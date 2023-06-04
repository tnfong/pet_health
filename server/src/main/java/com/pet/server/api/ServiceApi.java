package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.entity.Service;
import com.pet.server.service.IServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceApi {

    @Autowired
    private IServiceService serviceService;

    @GetMapping("/list")
    public CommonResponse<List<Service>> getList(){
        return CommonResponse.of(HttpStatus.OK, true, "", serviceService.getList());
    }

}
