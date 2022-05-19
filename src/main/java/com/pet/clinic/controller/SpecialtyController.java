package com.pet.clinic.controller;

import com.pet.clinic.entity.Specialty;
import com.pet.clinic.entity.Vet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.SpecialtyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    // a table with all speciaties.
    @GetMapping("/specialty")
    public String getAllSpecialty(Model model) {
        List<Specialty> specialtyList = specialtyService.getAllSpecialty();
        model.addAttribute("specialties", specialtyList);
        return "specialty-list";
    }

    // when clicking add specialty button. shows a form for adding.
    @GetMapping("/addSpecialty")
    public String addSpecialty(Model model) {
        Specialty specialty = new Specialty();
        model.addAttribute("specialty", specialty);
        return "add-specialty";
    }

    // when clicking the save button. redirect to the specialty page.
    @PostMapping("/saveSpecialty")
    public String saveSpecialty(@Valid @ModelAttribute("specialty") Specialty newSpecialty, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "add-specialty";
        }
        specialtyService.saveOrUpdateSpecialty(newSpecialty);
        return "redirect:/specialty";
    }

    // when clicking on modify for a specialty. shows a form for updateing.
    @GetMapping("/showSpecialtyForUpdate/{specialtyId}")
    public String showSpecialtyForUpdate(@PathVariable(value = "specialtyId") Long id, Model model) throws RecordNotFoundException {
        Specialty specialty = specialtyService.getSpecialtyById(id);
        model.addAttribute("specialty", specialty);
        return "update-specialty";
    }

    // when clicking update, redirect to the specialty page.
    @PostMapping("/updateSpecialty")
    public String updateSpecialty(@Valid @ModelAttribute("specialty") Specialty updateSpecialty, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()){
            return "update-specialty";
        }
        specialtyService.saveOrUpdateSpecialty(updateSpecialty);
        return "redirect:/specialty";
    }

    // when clicking delete, stays on the current page.
    @GetMapping("/deleteSpecialty/{specialtyId}")
    public String deleteSpecialty(@PathVariable(value = "specialtyId") Long id){
        specialtyService.deleteSpecialtyById(id);
        return "redirect: /specialty";
    }
    // select box for specialties, and choose one to get all the vets with this specialty.
    @GetMapping("/specialty/getVetBySpecialty/{specialtyId}")
    public String getVetBySpecialty(@PathVariable(value = "specialtyId") Long specialtyId, Model model) throws RecordNotFoundException {
        Set<Vet> vets = specialtyService.getVetBySpecialty(specialtyId);
        model.addAttribute("vets", vets);
        return "vet-by-specialty";
    }
}
