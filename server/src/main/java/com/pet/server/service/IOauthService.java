package com.pet.server.service;

import com.pet.server.dto.UserDTO;
import com.pet.server.entity.User;

public interface IOauthService {

    String login(UserDTO userDTO);
    User register(UserDTO userDTO);
}
