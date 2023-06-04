package com.pet.server.common.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonResponse<T> {
    private String code;
    private Boolean status;
    private T data;
    private String message;

    public static <T> CommonResponse<T> of(HttpStatus code, Boolean status, String message, T data){
        CommonResponse<T> res = new CommonResponse<>();
        res.setCode(String.valueOf(code.value()));
        res.status = status;
        res.data = data;
        res.message = message;
        return res;
    }
}
