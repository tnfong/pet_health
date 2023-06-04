package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.entity.Config;
import com.pet.server.service.IAnalysisService;
import com.pet.server.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigApi {

    @Autowired
    private IConfigService configService;

    @GetMapping
    public CommonResponse<Config> get(@RequestParam("key") String key){
        return CommonResponse.of(HttpStatus.OK, true, "", configService.getByKey(key));
    }
}
