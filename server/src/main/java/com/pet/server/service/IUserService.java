package com.pet.server.service;

import com.pet.server.dto.UserDTO;
import com.pet.server.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IUserService {

    Page<Map<String, Object>> getDataPage(UserDTO search);
    List<Map<String, Object>> list(UserDTO search);
    User getById(Integer id);
    User changePass(Integer id, String password);
    User save(UserDTO userDTO);
    UserDTO encrypt(UserDTO userDTO);
    UserDTO decrypt(UserDTO userDTO);
    Boolean init();
    Boolean delete(Integer id);
}
