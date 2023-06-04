package com.example.quanlypet.model;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Animal {
    private int id = 0;
    private int idUser;
    private String name;
    private String avatarUrl = "";
    private int age;
    private String species;
    private String description;
    private int idStatus;
    private String avatarUri;

    public Animal(int idUser, String name, String avatarUri, int age, String species, int idStatus, String description) {
        this.idUser = idUser;
        this.name = name;
        this.avatarUri = avatarUri;
        this.age = age;
        this.species = species;
        this.idStatus = idStatus;
        this.description = description;
    }

    public RequestBody getBodyId() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.id));
    }

    public RequestBody getBodyAvatarUrl() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.avatarUrl));
    }

    public RequestBody getBodyName() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.name));
    }

    public RequestBody getBodyAge() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.age));
    }

    public RequestBody getBodySpecies() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.species));
    }

    public RequestBody getBodyIdStatus() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.idStatus));
    }

    public RequestBody getBodyDescription() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(this.description));
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
}
