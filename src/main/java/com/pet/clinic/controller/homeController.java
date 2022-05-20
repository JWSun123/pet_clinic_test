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
        return "/index";
    }


}