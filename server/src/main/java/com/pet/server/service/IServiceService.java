package com.pet.server.service;

import com.pet.server.dto.ServiceDTO;
import com.pet.server.dto.UserDTO;
import com.pet.server.entity.Service;
import com.pet.server.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceService {

    Page<Service> getDataPage(ServiceDTO search);
    List<Service> getList();
    Service getById(Integer id);
    Service save(ServiceDTO serviceDTO);

}
