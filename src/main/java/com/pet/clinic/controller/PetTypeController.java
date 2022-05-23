package com.pet.clinic.controller;

import com.pet.clinic.entity.*;
import com.pet.clinic.exception.*;
import com.pet.clinic.service.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@Controller
public class PetTypeController {

    private final PetTypeService petTypeService;

    public PetTypeController(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    //all pet types table
    @GetMapping("/pet-type")
    public String getAllPetType(Model model){
        List<PetType> petTypes = petTypeService.getAllPetType();
        model.addAttribute("petTypes", petTypes);
        return "pet-type";
    }

    //form of adding pet type after click add button
    @GetMapping("/addPet_type")
    public String addPetType(Model model){
        PetType petType = new PetType();
        model.addAttribute("petType", petType);
        return "add-pet-type";
    }

    @PostMapping("/savePetType")
    public String savePetType(@Valid @ModelAttribute("petType") PetType newPetType, BindingResult result) throws RecordNotFoundException {
        if(result.hasErrors()){
            return "add-pet-type";
        }
        petTypeService.saveOrUpdatePetType(newPetType);
        return "redirect:/pet-type";
    }

    //updating form
    @GetMapping("/showUpdate/{petTypeId}")
    public String updatePetType(@PathVariable(value = "petTypeId") Long petTypeId, Model model) throws RecordNotFoundException {
        PetType petType = petTypeService.getPetTypeById(petTypeId);
        model.addAttribute("petType", petType);
        return "updatePetType";
    }

    @GetMapping("/deletePetType/{petTypeId}")
    public String deletePetType(@PathVariable(value = "petTypeId") Long petTypeId) {
        petTypeService.deletePetTypeById(petTypeId);
        return "redirect:/pet-type";
    }
}