package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.dto.UserDTO;
import com.pet.server.entity.User;
import com.pet.server.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class OAuthApi {

    @Autowired
    private IOauthService oauthService;

    @PostMapping("/login")
    public CommonResponse<String> doLogin(@RequestBody UserDTO userDTO){
        String id = oauthService.login(userDTO);
        return CommonResponse.of(HttpStatus.OK, id != null,(id != null) ? "Đăng nhập thành công" : "Đăng nhập thất bại", id);
    }

    @PostMapping("/register")
    public CommonResponse<User> doRegister(@RequestParam("username") String username
            , @RequestParam("password") String password
            , @RequestParam("full_name") String fullName
            , @RequestParam("phone") String phone
            , @RequestParam(value = "id_role", required = false, defaultValue = "3") Integer idRole
            , @RequestParam("mail") String mail){
        UserDTO userDTO = new UserDTO(username, password, fullName, phone, idRole, mail);
        User userInfo = oauthService.register(userDTO);
        return CommonResponse.of(HttpStatus.OK, userInfo != null,(userInfo != null) ? "Đăng ký thành công" : "Đăng ký thất bại", userInfo);
    }
}
