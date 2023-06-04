package com.pet.server.api;

import com.pet.server.common.response.CommonResponse;
import com.pet.server.dto.BookDTO;
import com.pet.server.entity.Book;
import com.pet.server.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Admin, Customer
 * */
@RestController
@RequestMapping("/api/book")
public class BookApi {

    @Autowired
    private IBookService bookService;

    /**
     * @apiNote {role is Admin}
     * @return List booking when user request
     * */
    @GetMapping("/list")
    public CommonResponse<List<Map<String, Object>>> list(@RequestHeader("uid") Integer uid, @RequestParam("id_status") Integer idStatus){
        BookDTO search = new BookDTO();
        search.setIdUser(uid);
        search.setIdStatus(idStatus);
        return CommonResponse.of(HttpStatus.OK, true, "", bookService.list(search));
    }

    @GetMapping("/{id}")
    public CommonResponse<Map<String, Object>> info(@PathVariable("id") Integer id){
        return CommonResponse.of(HttpStatus.OK, true, "", bookService.info(id));
    }

    @PostMapping("/decrypt")
    public CommonResponse<BookDTO> decrypt(@RequestBody BookDTO dto){
        return CommonResponse.of(HttpStatus.OK, true, "", bookService.decrypt(dto));
    }

    /**
     * @apiNote {role is Customer}
     * @return Booking information after user request
     * */
    @PostMapping("/save")
    public CommonResponse<Book> save(@RequestHeader("uid") Integer uid
            , @RequestParam(value = "id", required = false, defaultValue = "0") Integer id
            , @RequestParam("id_doctor") Integer idDoctor
            , @RequestParam("id_animal") Integer idAnimal
            , @RequestParam("id_status") Integer idStatus
            , @RequestParam("photo_url") String photoUrl
            , @RequestParam("time") Date time
            , @RequestParam("time_hold") Date timeHold
            , @RequestParam("description") String description
            , @RequestParam("id_service") Integer idService
            , @RequestParam(value = "photo_file", required = false) MultipartFile photoFile){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        bookDTO.setIdUser(uid);
        bookDTO.setIdDoctor(idDoctor);
        bookDTO.setIdAnimal(idAnimal);
        bookDTO.setIdStatus(idStatus);
        bookDTO.setPhotoUrl(photoUrl);
        bookDTO.setTime(time);
        bookDTO.setTimeHold(timeHold);
        bookDTO.setDescription(description);
        bookDTO.setIdService(idService);
        bookDTO.setPhotoFile(photoFile);
        boolean check = bookService.checkBooking(bookDTO);
        if(check){
            Book book = bookService.save(bookDTO);
            return CommonResponse.of(HttpStatus.OK, true, "", book);
        }
        return CommonResponse.of(HttpStatus.OK, false, "", null);
    }
}
