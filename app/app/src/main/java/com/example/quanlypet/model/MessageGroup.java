package com.example.quanlypet.model;

import java.util.Date;

public class MessageGroup {

    private Integer id;
    private String name;
    private Integer idUser;
    private Integer idFrom = 0;
    private String content;
    private String avatarUrl;
    private Integer read;
    private Integer totalUnRead;
    private Date createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Integer idFrom) {
        this.idFrom = idFrom;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public Integer getTotalUnRead() {
        return totalUnRead;
    }

    public void setTotalUnRead(Integer totalUnRead) {
        this.totalUnRead = totalUnRead;
    }

    public void addTotalUnRead(){
        this.totalUnRead++;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
