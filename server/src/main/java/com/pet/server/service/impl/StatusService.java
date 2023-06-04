package com.pet.server.service.impl;

import com.pet.server.entity.Status;
import com.pet.server.repository.StatusRepository;
import com.pet.server.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService implements IStatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<Status> getByTable(String name) {
        return statusRepository.findByTableName(name);
    }
}
