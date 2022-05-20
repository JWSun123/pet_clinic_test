package com.pet.clinic.controller;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.text.*;
import java.util.*;

@Controller
public class homeController {

    @GetMapping("/index")
    public String viewHomePage(Model model){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("thisDate", dateString);
        return "/index";
    }


}