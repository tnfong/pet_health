package com.pet.server.service.impl;

import com.pet.server.dto.BookDTO;
import com.pet.server.entity.Book;
import com.pet.server.entity.User;
import com.pet.server.repository.BookRepository;
import com.pet.server.service.IBookService;
import com.pet.server.service.IFileService;
import com.pet.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IUserService userService;

    @Override
    public Page<Map<String, Object>> getDataPage(BookDTO search) {
        return bookRepository.getDataPage(search, search.pageable());
    }

    @Override
    public List<Map<String, Object>> list(BookDTO search) {
        User user = userService.getById(search.getIdUser());
        if(user.getIdRole().equals(1)){
            search.setIdUser(0);
        } else if(user.getIdRole().equals(2)){
            search.setIdUser(0);
            search.setIdDoctor(user.getId());
        }
        return bookRepository.getList(search);
    }

    @Override
    public Map<String, Object> info(Integer id) {
        return bookRepository.getInfo(id);
    }

    @Override
    public Book getOne(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public boolean confirm(Integer id) {
        Book book = getOne(id);
        if(book.getIdStatus().equals(3)) book.setIdStatus(4);
        else if(book.getIdStatus().equals(4)) book.setIdStatus(5);
        else return false;
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean checkBooking(BookDTO bookDTO) {
        if(bookDTO.getId().equals(0)){
            List<Book> books =  bookRepository.getDataByTime(bookDTO.getTime(), bookDTO.getTimeHold());
            return books.size() == 0;
        }
        return true;
    }

    @Override
    public BookDTO decrypt(BookDTO bookDTO) {
        bookDTO.decrypt();
        return bookDTO;
    }

    @Override
    public Book save(BookDTO bookDTO) {
        Book book = new Book(bookDTO);
        if(bookDTO.getPhotoFile() != null && bookDTO.getPhotoFile().getSize() > 0){
            String avatarUrl = fileService.uploadFile(bookDTO.getPhotoFile(), "");
            book.setPhotoUrl(avatarUrl);
        }
        bookRepository.save(book);
        return book;
    }
}
