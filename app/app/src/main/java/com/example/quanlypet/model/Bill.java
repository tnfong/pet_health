package com.example.quanlypet.model;

import android.annotation.SuppressLint;

import com.example.quanlypet.common.utils.CryptoUtils;

import java.util.Date;

public class Bill {
    private int id;
    private int idUser;
    private int idBook;
    private Double price;
    private String userFullName;
    private String userPhone;
    private String serviceName;
    private String note;
    private String time;
    private Date createdDate;

    public void setData(Bill bill){
        this.userFullName = bill.getUserFullName();
        this.userPhone = bill.getUserPhone();
        this.serviceName = bill.getServiceName();
        this.price = bill.getPrice();
        this.note = bill.getNote();
        this.createdDate = bill.getCreatedDate();
    }

    @SuppressLint("NewApi")
    public void decrypt(){
        try {
            this.userFullName = CryptoUtils.AES.decrypt(this.userFullName);
            this.userPhone = CryptoUtils.AES.decrypt(this.userPhone);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
