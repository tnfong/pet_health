package com.pet.server.dto;

import lombok.Data;

import javax.persistence.*;

@Data
public class ConfigDTO {

    private Integer id;
    private String key;
    private String name;
    private String value;
}
