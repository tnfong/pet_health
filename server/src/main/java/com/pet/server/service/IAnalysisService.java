package com.pet.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IAnalysisService {

    List<Map<String, Object>> month();
    List<Map<String, Object>> week();

}