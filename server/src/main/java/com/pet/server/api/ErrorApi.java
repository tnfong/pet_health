package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/error")
public class ErrorApi {

    @GetMapping
    public CommonResponse<String> goError(){
        return CommonResponse.of(HttpStatus.BAD_REQUEST, false, "Yêu cầu thất bại", null);
    }
}
