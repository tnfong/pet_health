package com.pet.server.service.impl;

import com.pet.server.dto.ServiceDTO;
import com.pet.server.entity.Service;
import com.pet.server.repository.ServiceRepository;
import com.pet.server.service.IServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Page<Service> getDataPage(ServiceDTO search) {
        return serviceRepository.getDataPage(search, search.pageable());
    }

    @Override
    public List<Service> getList() {
        return serviceRepository.findAll();
    }

    @Override
    public Service getById(Integer id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public Service save(ServiceDTO serviceDTO) {
        Service service = new Service(serviceDTO);
        serviceRepository.save(service);
        return service;
    }
}
