package com.pet.clinic.controller;

import com.pet.clinic.entity.*;
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
    private final OwnerService ownerService;

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @GetMapping("/pet")
    public String getAllPets(Model model){
        List<Pet> petList = petService.getAllPets();
        model.addAttribute("pets", petList);
        return "pet/pet-list";
    }

    @GetMapping("/findOwner")
    public String showPage(){
        return "pet/find-owner";
    }

    @GetMapping("/addPet/{ownerId}")
    public String addPet(@PathVariable(value = "ownerId") Long ownerId, Model model) throws RecordNotFoundException {
        Owner owner = ownerService.getOwnerById(ownerId);
        Pet pet = new Pet();
        pet.setOwner(owner);
        model.addAttribute("owner", owner);
        model.addAttribute("pet", pet);
        model.addAttribute("petTypes", petTypeService.getAllPetType());
        return "pet/add-pet";
    }

    @PostMapping("/savePet")
    public String savePet(@Valid @ModelAttribute("pet") Pet newPet, Model model, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            model.addAttribute("owner", newPet.getOwner());
            return "pet/add-pet";
        }
        petService.saveOrUpdatePet(newPet, newPet.getOwner());
        return "redirect:/pet";
    }

    @GetMapping("/showPetInfoForUpdate/{petId}")
    public String showPetInfoForUpdate(@PathVariable(value = "petId") Long petId, Model model) throws RecordNotFoundException {
        Pet pet = petService.getPetById(petId);
        model.addAttribute("pet", pet);
        model.addAttribute("petTypes", petTypeService.getAllPetType());
        return "pet/update-pet-info";
    }

    @PostMapping("/updatePetInfo")
    public String updatePetInfo(@Valid @ModelAttribute("pet") Pet updatePet, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "pet/update-pet-info";
        }
        petService.saveOrUpdatePet(updatePet, updatePet.getOwner());
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
        return "pet/pet-list";
    }

    @GetMapping("/searchOwner")
    public String searchOwnerByKeyword(Model model, @RequestParam("keyword") String keyword) throws RecordNotFoundException {
        List<Owner> owners;
        if(keyword != null) {
            owners = ownerService.findOwnerByKeyword(keyword);
        }else{
            owners = ownerService.getAllOwners();
        }
        model.addAttribute("owners", owners);
        return "pet/pet-find-owner";
    }

}