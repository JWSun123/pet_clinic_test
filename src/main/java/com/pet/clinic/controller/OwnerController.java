package com.pet.clinic.controller;

import com.pet.clinic.entity.Owner;
import com.pet.clinic.entity.Pet;
import com.pet.clinic.entity.PetType;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.PetTypeRepository;
import com.pet.clinic.service.OwnerService;
import com.pet.clinic.service.PetService;
import com.pet.clinic.service.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OwnerController {

    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public OwnerController(OwnerService ownerService, PetService petService, PetTypeRepository petTypeRepository, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    //List of clients TODO: add pagination if there's a lot of clients.
    @GetMapping("/clients")
    public String getOwner(Model model) {
        List<Owner> ownerList = ownerService.getAllOwners();
        List<Pet> petList; // In pets table, I have pet_name associated with owner_id. I want to retrieve every pet and make it a string.
        //getPetByOwnerId(Long id)
        //String petNamesString = ownerService.nameAllPets();
        model.addAttribute("owner", ownerList);
        //pass concatenated pet's name

        //model.addAttribute("pet")
        return "owner-list";
    }

    //add new owner
    @GetMapping("/addClient")
    public String newOwner(Model model) {
        Owner owner = new Owner();
        Pet pet = new Pet();
        List<PetType> petNames = petTypeService.getAllPetType();
        model.addAttribute("owner", owner);
        model.addAttribute("pet", pet);
        model.addAttribute("petNames", petNames);
        return "add-owner";
    }


    //edit owner
    @GetMapping("/updateOwner/{id}")
    public String updateOwnerById(@PathVariable(value = "id") Long id, Model model) throws RecordNotFoundException {
        Owner owner = ownerService.getOwnerById(id);
        model.addAttribute("owner", owner);
        return "update-owner";
    }
    //delete
    @GetMapping("/deleteOwner/{id}")
    public String deleteOwnerById(@PathVariable(value = "id") Long id) {
        ownerService.deleteOwnerById(id);
        return "redirect:/clients";
    }
    //search owner by name/phone
    @PostMapping("/searchOwner")
    public String searchOwner(@RequestParam(value = "query")String query, Model model) throws RecordNotFoundException {
        //How to differentiate id vs tel? Maybe add pattern to tel to have this format: (123)123-1234?
        List<Owner> ownerList = new ArrayList<>();
        if(query.startsWith("(")) {
            ownerList.add(ownerService.getOwnerByTel(query));
        } else {
            ownerList.add(ownerService.getOwnerById(Long.valueOf(query)));
        }
        model.addAttribute("owner", ownerList);
        return "owner-list";
    }
    //Need to figure out to get pets

    //save existing or new owner with the pet
    @PostMapping("/saveClient")
    public String saveOwner(@Valid @ModelAttribute("owner") Owner owner, @ModelAttribute("pet")Pet pet, BindingResult result, Model model) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "add-owner";
        }
        ownerService.saveOrUpdateOwner(owner, pet);
        return "redirect:/clients";
    }
}
