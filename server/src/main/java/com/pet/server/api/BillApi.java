package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.dto.BillDTO;
import com.pet.server.entity.Bill;
import com.pet.server.entity.Book;
import com.pet.server.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Admin, Customer
 * */
@RestController
@RequestMapping("/api/bill")
public class BillApi {

    @Autowired
    private IBillService billService;

    /**
     * @apiNote {role is Admin}
     * @return List booking when user request
     * */
    @GetMapping("/list")
    public CommonResponse<List<Map<String, Object>>> list(@RequestHeader("uid") Integer uid){
        return CommonResponse.of(HttpStatus.OK, true, "", billService.list(uid));
    }

    @GetMapping("/init")
    public CommonResponse<BillDTO> init(@RequestParam("id_book") Integer idBook){
        return CommonResponse.of(HttpStatus.OK, true, "", billService.init(idBook));
    }

    /**
     * @apiNote {role is Customer}
     * @return Booking information after user request
     * */
    @PostMapping("/save")
    public CommonResponse<Bill> save(@RequestBody BillDTO billDTO){
        return CommonResponse.of(HttpStatus.OK, true, "", billService.save(billDTO));
    }
}
