package com.pet.server.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class PageDTO {
    private Integer page = 1; //Page
    private Integer row = 10; //Row

    public Pageable pageable(){
        return PageRequest.of(page-1, row);
    }

    public Integer getRow(){
        return this.row;
    }

    public Integer getPage(){
        return this.page;
    }
}
