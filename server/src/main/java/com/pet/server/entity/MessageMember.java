package com.pet.server.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "message_member")
public class MessageMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_group")
    private Integer idGroup;

    @Column(name = "id_user")
    private Integer idUser;

    public MessageMember() {
    }

    public MessageMember(Integer idGroup, Integer idUser){
        this.idGroup = idGroup;
        this.idUser = idUser;
    }
}
