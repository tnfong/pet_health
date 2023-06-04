package com.pet.server.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
public class AnimalDTO {

    private Integer id;
    private Integer idUser;
    private String avatarUrl;
    private String name;
    private Double age;
    private String species;
    private String description;
    private Integer idStatus;
    private MultipartFile avatarFile;
}
