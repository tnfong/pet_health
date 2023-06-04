package com.pet.server.entity;

import com.pet.server.dto.BookDTO;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "id_doctor")
    private Integer idDoctor;

    @Column(name = "id_animal")
    private Integer idAnimal;

    @Column(name = "id_status")
    private Integer idStatus;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "time")
    private Date time;

    @Column(name = "time_hold")
    private Date timeHold;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "id_service")
    private Integer idService;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    public Book() {
    }

    public Book(BookDTO bookDTO) {
        this.id = bookDTO.getId();
        this.idUser = bookDTO.getIdUser();
        this.idDoctor = bookDTO.getIdDoctor();
        this.idAnimal = bookDTO.getIdAnimal();
        this.idStatus = bookDTO.getIdStatus();
        this.photoUrl = bookDTO.getPhotoUrl();
        this.time = bookDTO.getTime();
        this.timeHold = bookDTO.getTimeHold();
        this.location = bookDTO.getLocation();
        this.description = bookDTO.getDescription();
        this.idService = bookDTO.getIdService();
    }
}
