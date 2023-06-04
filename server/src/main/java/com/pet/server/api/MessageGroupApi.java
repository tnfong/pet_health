package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.service.IMessageGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message/group")
public class MessageGroupApi {

    @Autowired
    private IMessageGroupService messageGroupService;

    @GetMapping("/list")
    public CommonResponse<List<Map<String, Object>>> list(@RequestHeader("uid") Integer uid){
        return CommonResponse.of(HttpStatus.OK, true, "", messageGroupService.list(uid));
    }

    @PostMapping("/create")
    public CommonResponse<Integer> create(@RequestHeader("uid") Integer uid, @RequestParam("id_user") Integer idUser){
        return CommonResponse.of(HttpStatus.OK, true, "", messageGroupService.create(uid, idUser));
    }
}
