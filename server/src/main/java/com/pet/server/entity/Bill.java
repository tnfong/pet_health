package com.pet.server.entity;

import com.pet.server.dto.BillDTO;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_book")
    private Integer idBook;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "price")
    private Double price;

    @Column(name = "note")
    private String note;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    public Bill() {
    }

    public Bill(BillDTO billDTO) {
        this.id = billDTO.getId();
        this.idBook = billDTO.getIdBook();
        this.idUser = billDTO.getIdUser();
        this.price = billDTO.getPrice();
        this.note = billDTO.getNote();
    }
}
