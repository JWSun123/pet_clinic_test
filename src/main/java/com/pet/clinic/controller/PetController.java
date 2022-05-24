package com.pet.clinic.controller;

import com.pet.clinic.entity.Pet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/pet")
    public String getAllPets(Model model){
        List<Pet> petList = petService.getAllPets();
        model.addAttribute("pets", petList);
        return "pet-list";
    }

    @GetMapping("/addPet")
    public String addPet(Model model){
        Pet pet = new Pet();
        model.addAttribute("pet", pet);
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

    // when clicking on the modify button beside a pet. show a form for update pet info(not include specialty)
    // a separate button for view/modify pet's specialty.
    @GetMapping("/showPetInfoForUpdate/{petId}")
    public String showPetInfoForUpdate(@PathVariable(value = "petId") Long petId, Model model) throws RecordNotFoundException {
        Pet pet = petService.getPetById(petId);
        model.addAttribute("pet", pet);
        return "update-pet-info";
    }

    //updating info, won't open any new page.
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
}
