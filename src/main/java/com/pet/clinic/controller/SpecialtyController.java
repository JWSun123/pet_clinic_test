package com.pet.clinic.controller;

import com.pet.clinic.entity.Specialty;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.SpecialtyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping("/specialty")
    public String getAllSpecialty(Model model) {
        List<Specialty> specialtyList = specialtyService.getAllSpecialty();
        model.addAttribute("specialties", specialtyList);
        return "specialty-list";
    }

    @GetMapping("/addSpecialty")
    public String addSpecialty(Model model) {
        Specialty specialty = new Specialty();
        model.addAttribute("specialty", specialty);
        return "add-specialty";
    }

    @PostMapping("/saveSpecialty")
    public String saveSpecialty(@Valid @ModelAttribute("specialty") Specialty newSpecialty, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "add-specialty";
        }
        specialtyService.saveOrUpdateSpecialty(newSpecialty);
        return "redirect:/specialty";
    }

    @GetMapping("/showSpecialtyForUpdate/{specialtyId}")
    public String showSpecialtyForUpdate(@PathVariable(value = "specialtyId") Long id, Model model) throws RecordNotFoundException {
        Specialty specialty = specialtyService.getSpecialtyById(id);
        model.addAttribute("specialty", specialty);
        return "update-specialty";
    }

    @PostMapping("/updateSpecialty")
    public String updateSpecialty(@Valid @ModelAttribute("specialty") Specialty updateSpecialty, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()){
            return "update-specialty";
        }
        specialtyService.saveOrUpdateSpecialty(updateSpecialty);
        return "redirect:/specialty";
    }

    @GetMapping("/deleteSpecialty/{specialtyId}")
    public String deleteSpecialty(@PathVariable(value = "specialtyId") Long id) throws RecordNotFoundException {
        specialtyService.deleteSpecialtyById(id);
        return "redirect: /specialty";
    }
}
