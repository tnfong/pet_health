package com.pet.server.entity;

import com.pet.server.dto.ConfigDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`key`")
    private String key;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    public Config() {
    }

    public Config(ConfigDTO configDTO) {
        this.id = configDTO.getId();
        this.key = configDTO.getKey();
        this.name = configDTO.getName();
        this.value = configDTO.getValue();
    }
}
