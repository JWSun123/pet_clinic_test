package com.pet.clinic.controller;

import com.pet.clinic.entity.Owner;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.OwnerService;
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

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    //get all owner (have pagination)
    @GetMapping("/clients")
    public String getOwner(Model model) {
        List<Owner> ownerList = ownerService.getAllOwners();
        //getPetByOwnerId(Long id)
        //String petNamesString = ownerService.nameAllPets();
        model.addAttribute("owner", ownerList);
        //pass concatenated pet's name

        //model.addAttribute("pet")
        return "owner-page";
    }

    //add new owner
    @GetMapping("/addClient")
    public String newOwner(Model model) {
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "add-owner";
    }

    //save existing or new owner
    @PostMapping("/saveClient")
    public String saveOwner(@Valid @ModelAttribute("owner") Owner owner,BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "add-owner";
        }
        ownerService.saveOrUpdateOwner(owner);
        return "redirect:/clients";
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
        return "owner-page";
    }
    //Need to figure out to get pets
}
