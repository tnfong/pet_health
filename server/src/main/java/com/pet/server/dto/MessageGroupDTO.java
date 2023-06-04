package com.pet.server.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
public class MessageGroupDTO {

    private Integer id;
    private Integer idUser;
    private String name;
    private Date createdDate;
    private Date updatedDate;
}
