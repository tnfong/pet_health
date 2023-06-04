package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.service.IAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisApi {

    @Autowired
    private IAnalysisService analysisService;

    @GetMapping("/month")
    public CommonResponse<List<Map<String, Object>>> month(){
        return CommonResponse.of(HttpStatus.OK, true, "", analysisService.month());
    }

    @GetMapping("/week")
    public CommonResponse<List<Map<String, Object>>> week(){
        return CommonResponse.of(HttpStatus.OK, true, "", analysisService.week());
    }
}
