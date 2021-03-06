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

    //Homepage for appointment, shows table of all appointments
    @GetMapping("/appointment")
    public String getAllAppointment(Model model) {

        List<Appointment> appointments = appointmentService.getAllAppointment();
        model.addAttribute("appointments", appointments);
        return "appointment/appointment-list";
    }

    //Search an appointment by the selected date
    @GetMapping("/appointmentByDate")
    public String getAppointmentByDate(@RequestParam("appointmentDate")String appointmentDate, Model model){
        List<Appointment> appointments = appointmentService.getAppointmentByDate(appointmentDate);
        model.addAttribute("appointments", appointments);
        return "appointment/appointment-list";
    }

    //Returns a page with pet search bar
    @GetMapping("/makeAppointment")
    public String showAppointmentPage() {
        return "appointment/appointment-step1";
    }

    //Search for a pet by keyword and shows them in a table
    @GetMapping("/findPetForAppointment")
    public String findPet(Model model, @RequestParam("keyword") String keyword) {
        List<Pet> pets;
        if (keyword != null) pets = petService.findPetByKeyword(keyword);
        else {
            pets = petService.getAllPets();
        }

        model.addAttribute("pets", pets);
        return "appointment/appointment-pet";
    }

    //Form to make an appointment
    @GetMapping("/makeAppointment/{petId}")
    public String makeAppointment(@PathVariable(value = "petId") Long petId, Model model) throws RecordNotFoundException {
        Pet pet = petService.getPetById(petId);
        Appointment appointment = new Appointment();
        appointment.setPet(pet);
        model.addAttribute("pet", pet);
        model.addAttribute("appointment", appointment);
        return "appointment/make-appointment";
    }

    //Save the appointment
    @PostMapping("/saveAppointment")
    public String saveAppointment(@Valid @ModelAttribute("appointment") Appointment newAppointment, BindingResult result, Model model) throws RecordNotFoundException {
        if(result.hasErrors()){
            // if I want to stay at the current page, petId is required.
            model.addAttribute("pet", newAppointment.getPet());
            return "appointment/make-appointment";
        }
        appointmentService.saveOrUpdateAppointment(newAppointment);
        return "redirect:/appointment";
    }

    //Delete the appointment
    @GetMapping("/cancelAppointment/{appointmentId}")
    public String cancelAppointment(@PathVariable(value = "appointmentId") Long appointmentId){
        appointmentService.cancelAppointment(appointmentId);
        return "redirect:../appointment";
    }
}
