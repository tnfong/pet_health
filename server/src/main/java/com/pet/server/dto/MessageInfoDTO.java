package com.pet.server.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.Date;

@Data
public class MessageInfoDTO extends PageDTO {

    private Integer id;
    private Integer idGroup;
    private Integer idUser;
    private Integer idParent = 0;
    private String content;
    private Date createdDate;
}
