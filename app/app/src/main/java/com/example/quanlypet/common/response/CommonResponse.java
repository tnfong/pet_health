package com.example.quanlypet.common.response;

public class CommonResponse<T> {

    private String code;
    private Boolean status;
    private String message;
    private T data;

    public Boolean getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
