package com.pet.clinic.controller;

import com.pet.clinic.entity.Appointment;
import com.pet.clinic.service.AppointmentService;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    private final AppointmentService appointmentService;

    public HomeController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/index")
    public String viewHomePage(Model model){
        String date = LocalDate.now().toString();
        List<Appointment> appointmentList = appointmentService.getAppointmentByDate(date);
        model.addAttribute("appointments", appointmentList);
        return "/index";
    }
}