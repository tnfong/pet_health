package com.pet.server.entity;

import com.pet.server.dto.ServiceDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    public Service() {
    }

    public Service(ServiceDTO serviceDTO) {
        this.id = serviceDTO.getId();
        this.name = serviceDTO.getName();
        this.price = serviceDTO.getPrice();
    }
}
