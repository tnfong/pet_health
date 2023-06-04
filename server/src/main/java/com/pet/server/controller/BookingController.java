package com.pet.server.controller;

import com.pet.server.common.utils.DataStatic;
import com.pet.server.common.utils.UrlUtils;
import com.pet.server.dto.BillDTO;
import com.pet.server.dto.BookDTO;
import com.pet.server.entity.Bill;
import com.pet.server.entity.Book;
import com.pet.server.entity.Guest;
import com.pet.server.entity.User;
import com.pet.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/book")
public class BookingController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAnimalService animalService;

    @Autowired
    private IBillService billService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private IGuestService guestService;

    @GetMapping({"", "/list"})
    public String goList(Model model, BookDTO search){
        model.addAttribute("search", search);
        model.addAttribute("statusList", statusService.getByTable(DataStatic.TABLE.BOOK));
        model.addAttribute("data", bookService.getDataPage(search));
        return "admin/component/book/list";
    }

    @PostMapping("/confirm/{id}")
    public String doUpdate(Model model, HttpServletRequest request, RedirectAttributes attr, @PathVariable("id") Integer id){
        boolean check = bookService.confirm(id);
        attr.addFlashAttribute("mess", (check) ? "Update fail" : "Update successful");
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }

    @GetMapping("/{id}/bill")
    public String goBill(Model model, @PathVariable("id") Integer idBook){
        model.addAttribute("billForm", billService.init(idBook));
        return "admin/component/book/bill";
    }

    @PostMapping("/{id}/bill")
    public String doBill(RedirectAttributes attr, @PathVariable("id") Integer id, @ModelAttribute("billForm") BillDTO billDTO){
        billDTO.setIdBook(id);
        Bill check = billService.save(billDTO);
        attr.addFlashAttribute("mess", "Update successful");
        return "redirect:/book/list";
    }

    @GetMapping("/{id}/guest")
    public String goGuest(Model model, @PathVariable("id") Integer id){
        Guest guest = guestService.getDataByIdBook(id);
        User doctor = userService.getById(guest.getIdDoctor());
        model.addAttribute("guestInfo", guest);
        model.addAttribute("doctorInfo", doctor);
        return "admin/component/book/guest";
    }
}
