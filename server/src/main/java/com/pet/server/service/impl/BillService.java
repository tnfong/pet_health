package com.pet.server.service.impl;

import com.pet.server.dto.BillDTO;
import com.pet.server.entity.Bill;
import com.pet.server.entity.Book;
import com.pet.server.entity.User;
import com.pet.server.repository.BillRepository;
import com.pet.server.repository.BookRepository;
import com.pet.server.service.IBillService;
import com.pet.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BillService implements IBillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IUserService userService;

    @Override
    public List<Map<String, Object>> list(Integer uid) {
        User user = userService.getById(uid);
        if(user.getIdRole().equals(1)){
            uid = 0;
        }
        return billRepository.getList(uid);
    }

    @Override
    public BillDTO init(Integer idBook) {
        BillDTO billDTO = new BillDTO();
        Map<String, Object> info = billRepository.initBill(idBook);
        billDTO.setUserFullName((String) info.get("userFullName"));
        billDTO.setUserPhone((String) info.get("userPhone"));
        billDTO.setServiceName((String) info.get("serviceName"));
        billDTO.setPrice((Double) info.get("servicePrice"));
        billDTO.setIdUser((Integer) info.get("idUser"));
        billDTO.setIdBook(idBook);
        Bill bill = billRepository.findByIdBook(idBook);
        if(bill != null){
            billDTO.setNote(bill.getNote());
            billDTO.setCreatedDate(bill.getCreatedDate());
        }
        return billDTO;
    }

    @Override
    public Bill save(BillDTO billDTO) {
        Book book = bookRepository.findById(billDTO.getIdBook()).orElse(new Book());
        Bill bill = billRepository.findByIdBook(billDTO.getIdBook());
        if(bill != null){
            bill.setPrice(billDTO.getPrice());
            bill.setNote(billDTO.getNote());
        }else{
            bill = new Bill(billDTO);
        }
        bill.setIdUser(book.getIdUser());
        billRepository.save(bill);
        return bill;
    }
}
