package com.pet.clinic.controller;

import com.pet.clinic.entity.Owner;
import com.pet.clinic.entity.Pet;
import com.pet.clinic.entity.PetType;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.OwnerService;
import com.pet.clinic.service.PetService;
import com.pet.clinic.service.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OwnerController {

    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public OwnerController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    //List of clients TODO: add pagination if there's a lot of clients.
    @GetMapping("/clients")
    public String getOwner(Model model) {
        List<Owner> ownerList = ownerService.getAllOwners();
        List<Pet> petList = petService.getAllPets();
        model.addAttribute("owner", ownerList);
        model.addAttribute("petList", petList);
        return "owner/owner-list";
    }

    //add new owner
    @GetMapping("/addClient")
    public String newOwner(Model model) {
        Owner owner = new Owner();
        Pet pet = new Pet();
        List<Pet> ownerPet = new ArrayList<>();
        ownerPet.add(pet);
        owner.setPet(ownerPet);
        List<PetType> petNames = petTypeService.getAllPetType();
        model.addAttribute("owner", owner);
        model.addAttribute("pet", pet);
        model.addAttribute("petNames", petNames);
        return "owner/add-owner";
    }


    //edit owner
    @GetMapping("/updateOwner/{id}")
    public String updateOwnerById(@PathVariable(value = "id") Long id, Model model) throws RecordNotFoundException {
        Owner owner = ownerService.getOwnerById(id);
        List<Pet> petList = petService.getAllPetByOwner(id);
        List<PetType> petNames = petTypeService.getAllPetType();
        model.addAttribute("owner", owner);
        model.addAttribute("petList", petList);
        model.addAttribute("petNames", petNames);
        return "owner/update-owner";
    }

    //delete
    @GetMapping("/deleteOwner/{id}")
    public String deleteOwnerById(@PathVariable(value = "id") Long id) {
        ownerService.deleteOwnerById(id);
        return "redirect:/clients";
    }
    //search owner by name/phone
    @GetMapping("/searchOwnerByKeyword")
    public String searchOwner(@RequestParam(value = "keyword")String keyword, Model model) throws RecordNotFoundException {
        List<Owner> ownerList;
        if(!keyword.isEmpty()) {
            ownerList = ownerService.getOwnerByKeyword(keyword);
        } else {
            ownerList = ownerService.getAllOwners();
        }
        model.addAttribute("owner", ownerList);
        return "owner/owner-list";
    }

    @PostMapping("/saveClient")
    public String saveOwner(@Valid @ModelAttribute("owner") Owner owner, BindingResult result, @ModelAttribute("pet")Pet pet, BindingResult petResult, Model model) throws RecordNotFoundException {
        List<PetType> petNames = petTypeService.getAllPetType();
        if (result.hasErrors() || petResult.hasErrors()) {
            if (owner.getId() == null) {
                List<Pet> ownerPet = new ArrayList<>();
                ownerPet.add(pet);
                owner.setPet(ownerPet);
                model.addAttribute("petNames", petNames);
                return "owner/add-owner";
            }
            model.addAttribute("petNames", petNames);
            return "owner/update-owner";
        }
        List<Pet> ownerPet = owner.getPet();
        ownerService.saveOrUpdateOwner(owner, ownerPet);

        ownerService.saveOrUpdateOwner(owner, owner.getPet());
        return "redirect:/clients";
    }
}
