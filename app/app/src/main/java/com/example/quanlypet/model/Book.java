package com.example.quanlypet.model;

import android.annotation.SuppressLint;

import com.example.quanlypet.common.utils.CryptoUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Book {
    private int id = 0; // id của lịch
    private int idUser; // id của người đặt
    private String userFullName; // tên cửa người đặt
    private String userAvatarUrl;
    private String userPhone;
    private int idDoctor;
    private String doctorFullName;
    private String doctorAvatarUrl;
    private String doctorPhone;
    private int idAnimal;
    private String animalName;
    private String animalAvatarUrl;
    private String animalSpecies;
    private int idStatus;
    private String statusName;
    private String photoUrl;
    private String photoUri;
    private Date time;
    private Date timeHold;
    private String address;
    private String serviceName;
    private String createdDate;
    private String updatedDate;
    private Integer idService;
    private Integer idGuest;
    private Integer idBill;
    private String description;

    public Book() { }

    public void setData(int idUser, String description, String photoUri, String time, String timeHold, int idStatus){
        this.idUser = idUser;
        this.description = description;
        this.photoUri = photoUri;
        try {
            this.time = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(time);
            this.timeHold = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(timeHold);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.idStatus = idStatus;
    }

    @SuppressLint("NewApi")
    public void decrypt(){
        try {
            this.userFullName = CryptoUtils.AES.decrypt(this.userFullName);
            this.userPhone = CryptoUtils.AES.decrypt(this.userPhone);
            this.doctorFullName = CryptoUtils.AES.decrypt(this.doctorFullName);
            this.doctorPhone = CryptoUtils.AES.decrypt(this.doctorPhone);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAnimalSpecies() {
        return animalSpecies;
    }

    public Integer getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(Integer idGuest) {
        this.idGuest = idGuest;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public RequestBody getBodyId() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.id));
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    public Integer getIdBill() {
        return idBill;
    }

    public void setIdBill(Integer idBill) {
        this.idBill = idBill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public String getDoctorAvatarUrl() {
        return doctorAvatarUrl;
    }

    public void setDoctorAvatarUrl(String doctorAvatarUrl) {
        this.doctorAvatarUrl = doctorAvatarUrl;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalAvatarUrl() {
        return animalAvatarUrl;
    }

    public void setAnimalAvatarUrl(String animalAvatarUrl) {
        this.animalAvatarUrl = animalAvatarUrl;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getTimeHold() {
        return timeHold;
    }

    public RequestBody getBodyTimeHold() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String strDate = formatter.format(this.timeHold);
        return RequestBody.create(MediaType.parse("multipart/form-data"), strDate);
    }

    public void setTimeHold(Date timeHold) {
        this.timeHold = timeHold;
    }

    public int getIdStatus() {
        return idStatus;
    }
    public RequestBody getBodyIdStatus() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.idStatus));
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public RequestBody getBodyIdDoctor() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.idDoctor));
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getIdAnimal() {
        return idAnimal;
    }
    public RequestBody getBodyIdAnimal() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.idAnimal));
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getStatus() {
        return statusName;
    }

    public void setStatus(String status) {
        this.statusName = status;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public RequestBody getBodyPhotoUrl() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.photoUrl));
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getTime() {
        return time;
    }

    public String getTimeText() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String strDate = formatter.format(this.time);
        return strDate;
    }

    public RequestBody getBodyTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String strDate = formatter.format(this.time);
        return RequestBody.create(MediaType.parse("multipart/form-data"), strDate);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public RequestBody getBodyDescription() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.description));
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceName() {
        return serviceName;
    }

    public RequestBody getBodyIdService() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.idService));
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
