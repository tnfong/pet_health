package com.pet.server.entity;

import com.pet.server.dto.AnimalDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Double age;

    @Column(name = "species")
    private String species;

    @Column(name = "description")
    private String description;

    @Column(name = "id_status")
    private Integer idStatus;

    public Animal() {
    }

    public Animal(AnimalDTO animalDTO) {
        this.id = animalDTO.getId();
        this.idUser = animalDTO.getIdUser();
        this.avatarUrl = animalDTO.getAvatarUrl();
        this.name = animalDTO.getName();
        this.age = animalDTO.getAge();
        this.species = animalDTO.getSpecies();
        this.description = animalDTO.getDescription();
        this.idStatus = animalDTO.getIdStatus();
    }
}
