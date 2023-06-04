package com.pet.server.service;

import com.pet.server.dto.BillDTO;
import com.pet.server.entity.Bill;

import java.util.List;
import java.util.Map;

public interface IBillService {
    List<Map<String, Object>> list(Integer uid);
    BillDTO init(Integer idBook);
    Bill save(BillDTO billDTO);
}
