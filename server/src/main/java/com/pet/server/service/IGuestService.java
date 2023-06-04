package com.pet.server.service;

import com.pet.server.dto.GuestDTO;
import com.pet.server.entity.Guest;

import java.util.List;
import java.util.Map;

public interface IGuestService {
    List<Map<String, Object>> getList(Integer idAnimal);
    Guest getDataByIdBook(Integer idBook);
    Guest save(GuestDTO guestDTO);
}
