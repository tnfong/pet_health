package com.pet.server.service.impl;

import com.pet.server.dto.GuestDTO;
import com.pet.server.entity.Guest;
import com.pet.server.repository.GuestRepository;
import com.pet.server.service.IBookService;
import com.pet.server.service.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GuestService implements IGuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private IBookService bookService;

    @Override
    public List<Map<String, Object>> getList(Integer idAnimal) {
        return guestRepository.getList(idAnimal);
    }

    @Override
    public Guest getDataByIdBook(Integer idBook) {
        return guestRepository.findByIdBook(idBook);
    }

    @Override
    public Guest save(GuestDTO guestDTO) {
        Guest guest = new Guest(guestDTO);
        guestRepository.save(guest);
        bookService.confirm(guest.getIdBook());
        return guest;
    }
}
