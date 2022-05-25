package com.pet.clinic.controller;

import com.pet.clinic.entity.Appointment;
import com.pet.clinic.entity.Pet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.AppointmentService;
import com.pet.clinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String getAllAppointment(Model model) {

        List<Appointment> appointments = appointmentService.getAllAppointment();
        model.addAttribute("appointments", appointments);
        return "appointment-list";
    }

    @GetMapping("/appointmentByDate")
    public String getAppointmentByDate(@RequestParam("appointmentDate")String appointmentDate, Model model){
        List<Appointment> appointments = appointmentService.getAppointmentByDate(appointmentDate);
        model.addAttribute("appointments", appointments);
        return "appointment-list";
    }

    @GetMapping("/makeAppointment")
    public String showAppointmentPage() {
        return "appointment-step1";
    }

    @GetMapping("/findPetForAppointment")
    public String findPet(Model model, @RequestParam("keyword") String keyword) throws RecordNotFoundException {
        List<Pet> pets;
        if (keyword != null) pets = petService.findPetByKeyword(keyword);
        else {
            pets = petService.getAllPets();
        }

        model.addAttribute("pets", pets);
        return "appointment-pet";
    }

    @GetMapping("/makeAppointment/{petId}")
    public String makeAppointment(@PathVariable(value = "petId") Long petId, Model model) throws RecordNotFoundException {
        Pet pet = petService.getPetById(petId);
        Appointment appointment = new Appointment();
        appointment.setPet(pet);
        model.addAttribute("pet", pet);
        model.addAttribute("appointment", appointment);
        return "make-appointment";
    }

    @PostMapping("/saveAppointment")
    public String saveAppointment(@ModelAttribute("appointment") Appointment newAppointment) throws RecordNotFoundException {
//        if(result.hasErrors()){
//            return "make-appointment";
//        }
        appointmentService.saveOrUpdateAppointment(newAppointment);
        return "redirect:/appointment";
    }

    @GetMapping("/cancelAppointment/{appointmentId}")
    public String cancelAppointment(@PathVariable(value = "appointmentId") Long appointmentId){
        appointmentService.cancelAppointment(appointmentId);
        return "redirect:../appointment";
    }
}
