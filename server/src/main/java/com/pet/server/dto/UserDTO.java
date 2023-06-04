package com.pet.server.dto;

import com.pet.server.common.utils.CryptoUtils;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class UserDTO extends PageDTO {
    private Integer id;
    private String username = "";
    private String password;
    private String fullName = "";
    private String phone = "";
    private String email = "";
    private Integer gender = 1;
    private Integer idRole = 0;
    private String specialize;
    private String avatarUrl;
    private MultipartFile avatarFile;
    private Date createdDate;

    public UserDTO(){
        super();
    }

    public UserDTO(String username){
        super();
        this.username = username;
    }

    public UserDTO(Integer id){
        super();
        this.id = id;
    }

    public UserDTO(Integer id, String username){
        super();
        this.id = id;
        this.username = username;
    }

    public UserDTO(String username, String password, String fullName, String phone, Integer idRole, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.idRole = idRole;
    }

    public void encrypt(){
        try {
            this.fullName = CryptoUtils.AES.encrypt(this.fullName);
            this.email = CryptoUtils.AES.encrypt(this.email);
            this.phone = CryptoUtils.AES.encrypt(this.phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(){
        try {
            this.fullName = CryptoUtils.AES.decrypt(this.fullName);
            this.email = CryptoUtils.AES.decrypt(this.email);
            this.phone = CryptoUtils.AES.decrypt(this.phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
