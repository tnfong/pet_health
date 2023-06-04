package com.pet.server.entity;

import com.pet.server.common.utils.CryptoUtils;
import com.pet.server.dto.UserDTO;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Integer gender = 0;

    @Column(name = "id_role")
    private Integer idRole = 1;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "specialize")
    private String specialize;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    public User(){ }

    public User(UserDTO userDTO){
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.fullName = userDTO.getFullName();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.idRole = userDTO.getIdRole();
        this.gender = userDTO.getGender();
    }

    public void setData(UserDTO userDTO){
        if(userDTO.getPassword() != null && userDTO.getPassword().length() > 0) this.password = CryptoUtils.MD5.encode(userDTO.getPassword());
        this.fullName = userDTO.getFullName();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.idRole = userDTO.getIdRole();
        this.gender = userDTO.getGender();
        this.specialize = userDTO.getSpecialize();
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
