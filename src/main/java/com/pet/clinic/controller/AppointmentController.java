package com.pet.clinic.controller;

import com.pet.clinic.entity.Appointment;
import com.pet.clinic.entity.Pet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.AppointmentService;
import com.pet.clinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final PetService petService;

    public AppointmentController(AppointmentService appointmentService, PetService petService) {
        this.appointmentService = appointmentService;
        this.petService = petService;
    }

    @GetMapping("/appointment")
    public String getAllAppointment(Model model){
        List<Appointment> appointments = appointmentService.getAllAppointment();
        model.addAttribute("appointments", appointments);
        return "appointment-list";
    }

    @GetMapping("/makeAppointment")
    public String makeAppointment(Model model){
        return "appointment-step1";
    }

    @GetMapping("/findPetForAppointment")
    public String findPet(Model model, @RequestParam("keyword") String keyword) throws RecordNotFoundException {
        List<Pet> pets = null;
        if (keyword != null) pets = petService.findPetByKeyword(keyword);

        //TODO: delete " = null",adding "else {pets = petService.getAllPets();}"

        model.addAttribute("pets", pets);
        return "appointment-pet";
    }

}
