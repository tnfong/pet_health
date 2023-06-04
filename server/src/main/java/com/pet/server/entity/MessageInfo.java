package com.pet.server.entity;

import com.pet.server.dto.MessageInfoDTO;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "message_info")
public class MessageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_group")
    private Integer idGroup;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "readed")
    private Integer read = 0;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    public MessageInfo() {
    }

    public MessageInfo(MessageInfoDTO messageDTO) {
        this.idGroup = messageDTO.getIdGroup();
        this.idUser = messageDTO.getIdUser();
        this.content = messageDTO.getContent();
    }
}
