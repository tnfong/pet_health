package com.pet.server.dto;

import lombok.Data;

import javax.persistence.*;

@Data
public class ServiceDTO extends PageDTO{

    private Integer id;
    private String name = "";
    private Double price;
}
