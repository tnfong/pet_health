package com.pet.server.dto;

import com.pet.server.common.utils.CryptoUtils;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class BookDTO extends PageDTO{

    private Integer id;
    private Integer idUser = 0;
    private Integer idDoctor = 0;
    private Integer idAnimal = 0;
    private Integer idStatus = 0;
    private String photoUrl;
    private MultipartFile photoFile;
    private Date time;
    private Date timeHold;
    private String location;
    private String description;
    private Integer idService;
    private Date createdDate;
    private Date updatedDate;
    private String userFullName;
    private String userPhone;
    private String doctorFullName;
    private String doctorPhone;

    public void decrypt(){
        try {
            this.userFullName = CryptoUtils.AES.decrypt(this.userFullName);
            this.userPhone = CryptoUtils.AES.decrypt(this.userPhone);
            this.doctorFullName = CryptoUtils.AES.decrypt(this.doctorFullName);
            this.doctorPhone = CryptoUtils.AES.decrypt(this.doctorPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
