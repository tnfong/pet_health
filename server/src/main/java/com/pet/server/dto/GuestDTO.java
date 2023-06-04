package com.pet.server.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
public class GuestDTO {

    private Integer id;
    private Integer idDoctor;
    private Integer idAnimal;
    private Integer idBook;
    private String symptoms;
    private String diagnostic;
    private String instructions;
    private Date createdDate;
    private Date updatedDate;
}
