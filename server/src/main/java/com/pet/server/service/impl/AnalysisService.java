package com.pet.server.service.impl;

import com.pet.server.repository.BillRepository;
import com.pet.server.service.IAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisService implements IAnalysisService {

    @Autowired
    private BillRepository billRepository;

    @Override
    public List<Map<String, Object>> month() {
        return billRepository.analysisByMonth();
    }

    @Override
    public List<Map<String, Object>> week() {
        return billRepository.analysisByWeek();
    }
}
