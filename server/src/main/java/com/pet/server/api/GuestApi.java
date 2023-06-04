package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.dto.GuestDTO;
import com.pet.server.entity.Guest;
import com.pet.server.service.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/guest")
public class GuestApi {

    @Autowired
    private IGuestService guestService;

    @GetMapping("/list")
    public CommonResponse<List<Map<String, Object>>> list(@RequestHeader("uid") Integer uid, @RequestParam("id_animal") Integer idAnimal){
        return CommonResponse.of(HttpStatus.OK, true, "", guestService.getList(idAnimal));
    }

    @PostMapping("/save")
    public CommonResponse<Guest> save(@RequestHeader("uid") Integer uid, @RequestBody GuestDTO guestDTO){
        return CommonResponse.of(HttpStatus.OK, true, "", guestService.save(guestDTO));
    }
}
