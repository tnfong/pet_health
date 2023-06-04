package com.pet.server.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
public class BillDTO {
    private Integer id;
    private Integer idBook;
    private Integer idUser;
    private String userFullName;
    private String userPhone;
    private String serviceName;
    private Double price;
    private String note;
    private Date createdDate;
}
