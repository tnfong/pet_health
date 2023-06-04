package com.pet.server.service;

import com.pet.server.dto.BookDTO;
import com.pet.server.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IBookService {

    Page<Map<String, Object>> getDataPage(BookDTO search);
    List<Map<String, Object>> list(BookDTO search);
    Map<String, Object> info(Integer id);
    Book getOne(Integer id);
    boolean confirm(Integer id);
    boolean checkBooking(BookDTO bookDTO);
    BookDTO decrypt(BookDTO bookDTO);
    Book save(BookDTO bookDTO);
}
