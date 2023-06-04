package com.pet.server.service.impl;

import com.pet.server.common.utils.CryptoUtils;
import com.pet.server.repository.*;
import com.pet.server.service.IFileService;
import com.pet.server.service.IUserService;
import com.pet.server.dto.UserDTO;
import com.pet.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IFileService fileService;

    @Override
    public Page<Map<String, Object>> getDataPage(UserDTO search) {
        return userRepository.getDataPage(search, search.pageable());
    }

    @Override
    public List<Map<String, Object>> list(UserDTO search) {
        return userRepository.getList(search);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User changePass(Integer id, String password) {
        password = CryptoUtils.MD5.encode(password);
        User user = getById(id);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    @Override
    public User save(UserDTO userDTO) {
        User user;
        if(userDTO.getId() != 0){
           user = getById(userDTO.getId());
        }else{
            user = new User();
            user.setUsername(userDTO.getUsername());
        }

        user.setData(userDTO);
        if(userDTO.getAvatarFile() != null && userDTO.getAvatarFile().getSize() > 0){
            String avatarUrl = fileService.uploadFile(userDTO.getAvatarFile(), "1");
            user.setAvatarUrl(avatarUrl);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDTO encrypt(UserDTO userDTO) {
        userDTO.encrypt();
        return userDTO;
    }

    @Override
    public UserDTO decrypt(UserDTO userDTO) {
        userDTO.decrypt();
        return userDTO;
    }

    @Override
    public Boolean init() {
        List<User> users = userRepository.findAll();
        users.forEach(el -> {
            el.encrypt();
            userRepository.save(el);
        });
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        billRepository.deleteByUser(id);
        guestRepository.deleteByUser(id);
        bookRepository.deleteByIdUser(id);
        animalRepository.removeByIdUser(id);
        userRepository.deleteById(id);
        return true;
    }
}
