package com.pet.clinic.controller;

import com.pet.clinic.entity.Pet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PetController {
    private final PetService petService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @GetMapping("/pet")
    public String getAllPets(Model model){
        List<Pet> petList = petService.getAllPets();
        model.addAttribute("pets", petList);
        return "pet";
    }

    @GetMapping("/addPet")
    public String addPet(Model model){
        Pet pet = new Pet();
        model.addAttribute("pet", pet);
        model.addAttribute("petTypes", petTypeService.getAllPetType());
        return "add-pet";
    }

    @PostMapping("/savePet")
    public String savePet(@Valid @ModelAttribute("pet") Pet newPet, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "/add-pet";
        }
        petService.saveOrUpdatePet(newPet);
        return "redirect:/pet";
    }


    @GetMapping("/showPetInfoForUpdate/{petId}")
    public String showPetInfoForUpdate(@PathVariable(value = "petId") Long petId, Model model) throws RecordNotFoundException {
        Pet pet = petService.getPetById(petId);
        model.addAttribute("pet", pet);
        return "update-pet-info";
    }

    @PostMapping("/updatePetInfo")
    public String updatePetInfo(@Valid @ModelAttribute("pet") Pet updatePet, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "/update-pet-info";
        }
        petService.saveOrUpdatePet(updatePet);
        return "redirect:/pet";
    }

    @GetMapping("/deletePet/{petId}")
    public String deletePet(@PathVariable(value = "petId") Long petId) {
        petService.deletePet(petId);
        return "redirect:../pet";
    }

    @GetMapping("/searchPet")
    public String searchPetByKeyword(Model model, @RequestParam("keyword") String keyword) throws RecordNotFoundException{
        List<Pet> pets;
        if (keyword != null) pets = petService.findPetByKeyword(keyword);
        else {pets = petService.getAllPets();
        }
        model.addAttribute("pets", pets);
        return "pet";
    }
}