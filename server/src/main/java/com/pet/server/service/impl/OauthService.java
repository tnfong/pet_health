package com.pet.server.service.impl;

import com.pet.server.common.utils.CryptoUtils;
import com.pet.server.dto.UserDTO;
import com.pet.server.entity.User;
import com.pet.server.repository.UserRepository;
import com.pet.server.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthService implements IOauthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(UserDTO userDTO) {
        User userInfo = userRepository.findByUsername(userDTO.getUsername());
        if(userInfo == null) return null;
        if(userInfo.getPassword().equals(CryptoUtils.MD5.encode(userDTO.getPassword()))){
            return userInfo.getId()+"";
        }
        return null;
    }

    @Override
    public User register(UserDTO userDTO) {
        User userInfo = userRepository.findByUsername(userDTO.getUsername());
        if(userInfo != null) return null;
        userInfo = new User(userDTO);
        userInfo.setPassword(CryptoUtils.MD5.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        return userInfo;
    }
}
