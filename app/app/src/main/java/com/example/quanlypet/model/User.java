package com.example.quanlypet.model;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.quanlypet.common.utils.CryptoUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class User {

    private int id;  // id người dùng
    private String username; // username ng dùng
    private String password; // mật khẩu
    private String fullName; // tên người dùng
    private String email; // emial
    private String phone; // sdt
    private int gender; // giới tính
    private Integer idRole = 3;// vai trò
    private String avatarUrl = ""; // link ảnh đại điện
    private String avatarFileUri;
    private String specialize;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
        this.password = "1234";
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String name, int img, String phone) {
        this.fullName = name;
//        this.avatarUrl = img;
        this.phone = phone;
    }

    public User(String username, String fullName, String email, String phone, int gender, String password) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
    }

    @SuppressLint("NewApi")
    public void encrypt(){
        try {
            this.fullName = CryptoUtils.AES.encrypt(this.fullName);
            this.phone = CryptoUtils.AES.encrypt(this.phone);
            this.email = CryptoUtils.AES.encrypt(this.email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("NewApi")
    public void decrypt(){
        try {
            this.fullName = CryptoUtils.AES.decrypt(this.fullName);
            this.phone = CryptoUtils.AES.decrypt(this.phone);
            this.email = CryptoUtils.AES.decrypt(this.email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RequestBody getBodyId() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.id));
    }

    public RequestBody getBodyUsername() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.username));
    }

    public RequestBody getBodyPassword() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.password));
    }

    public RequestBody getBodyFullName() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.fullName));
    }

    public RequestBody getBodyEmail() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.email));
    }

    public RequestBody getBodyPhone() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.phone));
    }

    public RequestBody getBodyGender() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.gender));
    }

    public RequestBody getBodyIdRole() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.idRole));
    }

    public RequestBody getBodyAvatarUrl() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.avatarUrl));
    }

    public RequestBody getBodySpecialize() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.specialize));
    }

    public MultipartBody.Part getBodyAvatarFile(){
        if(this.avatarFileUri != null && this.avatarFileUri.length() > 0){
            File file = new File(this.avatarFileUri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return MultipartBody.Part.createFormData("avatar_file", file.getName(), requestFile);
        }else{
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            return MultipartBody.Part.createFormData("avatar_file", "", requestFile);
        }
    }

    public String getSpecialize() {
        return specialize;
    }

    public void setSpecialize(String specialize) {
        this.specialize = specialize;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarFileUri() {
        return avatarFileUri;
    }

    public void setAvatarFileUri(String avatarFileUri) {
        this.avatarFileUri = avatarFileUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
