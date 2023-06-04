package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.service.IMessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message/info")
public class MessageInfoApi {

    @Autowired
    private IMessageInfoService messageInfoService;

    @GetMapping("/list")
    public CommonResponse<List<Map<String, Object>>> list(@RequestHeader("uid") Integer uid
            , @RequestParam("id_group") Integer idGroup){
        return CommonResponse.of(HttpStatus.OK, true, "", messageInfoService.list(uid, idGroup));
    }
}
