package com.pet.server.entity;

import com.pet.server.dto.GuestDTO;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "guest")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_doctor")
    private Integer idDoctor;

    @Column(name = "id_animal")
    private Integer idAnimal;

    @Column(name = "id_book")
    private Integer idBook;

    @Column(name = "symptoms")
    private String symptoms;

    @Column(name = "diagnostic")
    private String diagnostic;

    @Column(name = "instructions")
    private String instructions;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    public Guest() {

    }

    public Guest(GuestDTO guestDTO) {
        this.idBook = guestDTO.getIdBook();
        this.idDoctor = guestDTO.getIdDoctor();
        this.idAnimal = guestDTO.getIdAnimal();
        this.symptoms = guestDTO.getSymptoms();
        this.diagnostic = guestDTO.getDiagnostic();
        this.instructions = guestDTO.getInstructions();
    }
}
