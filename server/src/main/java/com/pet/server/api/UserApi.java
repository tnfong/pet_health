package com.pet.server.api;

import com.pet.server.dto.UserDTO;
import com.pet.server.entity.Book;
import com.pet.server.entity.User;
import com.pet.server.service.IUserService;
import com.pet.server.common.response.CommonResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public CommonResponse<List<Map<String, Object>>> list(@RequestParam("id_role") Integer idRole){
        UserDTO search = new UserDTO();
        search.setIdRole(idRole);
        return CommonResponse.of(HttpStatus.OK, true, "Success", userService.list(search));
    }

    @GetMapping("/{id}")
    public CommonResponse<User> getById(@PathVariable("id") Integer id){
        return CommonResponse.of(HttpStatus.OK, true, "Success", userService.getById(id));
    }

    @GetMapping("/info")
    public CommonResponse<User> getInfo(@RequestHeader("uid") Integer uid){
        User userInfo = userService.getById(uid);
        return CommonResponse.of(HttpStatus.OK, userInfo != null,(userInfo != null) ? "Success" : "Fail", userInfo);
    }

    @GetMapping("/init")
    public CommonResponse<Boolean> init(@RequestHeader("uid") Integer uid){
        Boolean check = userService.init();
        return CommonResponse.of(HttpStatus.OK, check, check ? "Success" : "Fail", check);
    }

    @PostMapping("/changePass")
    public CommonResponse<User> changePass(@RequestHeader("uid") Integer uid, @RequestParam("password") String password){
        User userInfo = userService.changePass(uid, password);
        return CommonResponse.of(HttpStatus.OK, true, "Success", userInfo);
    }

    @PostMapping("/decrypt")
    public CommonResponse<UserDTO> decrypt(@RequestHeader("uid") Integer uid, @RequestBody UserDTO userDTO){
        UserDTO user = userService.decrypt(userDTO);
        return CommonResponse.of(HttpStatus.OK, true, "Success", user);
    }

    @PostMapping("/encrypt")
    public CommonResponse<UserDTO> encrypt(@RequestHeader("uid") Integer uid, @RequestBody UserDTO userDTO){
        UserDTO user = userService.encrypt(userDTO);
        return CommonResponse.of(HttpStatus.OK, true, "Success", user);
    }

    @PostMapping("/save")
    public CommonResponse<User> save(@RequestHeader("uid") Integer uid
            , @RequestParam("username") String username
            , @RequestParam("password") String password
            , @RequestParam("full_name") String fullName
            , @RequestParam("email") String email
            , @RequestParam("phone") String phone
            , @RequestParam("gender") Integer gender
            , @RequestParam("id_role") Integer idRole
            , @RequestParam("avatar_url") String avatarUrl
            , @RequestParam("specialize") String specialize
            , @RequestParam(name = "avatar_file", required = false) MultipartFile avatarFile){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(uid);
        userDTO.setPassword(password);
        userDTO.setFullName(fullName);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);
        userDTO.setGender(gender);
        userDTO.setIdRole(idRole);
        userDTO.setAvatarUrl(avatarUrl);
        userDTO.setAvatarFile(avatarFile);
        userDTO.setSpecialize(specialize);
        User userInfo = userService.save(userDTO);
        return CommonResponse.of(HttpStatus.OK, true, "Success", userInfo);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> delete(@PathVariable("id") Integer id){
        return CommonResponse.of(HttpStatus.OK, true, "Success", userService.delete(id));
    }
}
