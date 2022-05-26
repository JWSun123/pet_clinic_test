package com.pet.clinic.controller;

import com.pet.clinic.entity.Pet;
import com.pet.clinic.entity.Vet;
import com.pet.clinic.entity.Visit;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.PetService;
import com.pet.clinic.service.VetService;
import com.pet.clinic.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;
    private final VetService vetService;

    public VisitController(VisitService visitService, PetService petService, VetService vetService) {
        this.visitService = visitService;
        this.petService = petService;
        this.vetService = vetService;
    }

    @GetMapping("/visit")
    public String getAllVisit(Model model){
        List<Visit> visits = visitService.getAllVisit();
        model.addAttribute("visits", visits);
        return "visit/visit-list";
    }

    @GetMapping("/addVisit")
    public String showStep1(){
        return "visit/visit-step1";
    }

    @GetMapping("/findPetForAddVisit")
    public String findPetForAddVisit(@RequestParam(value = "keyword")String keyword, Model model) {
        List<Pet> pets;
        if(keyword != null) pets = petService.findPetByKeyword(keyword);
        else pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "visit/visit-pet";
    }

    @GetMapping("/addVisitDetail/{petId}")
    public String addVisit(@PathVariable(value = "petId")Long petId, Model model) throws RecordNotFoundException {
        Pet pet = petService.getPetById(petId);
        Visit visit = new Visit();
        visit.setPet(pet);

        // get all the vets for the select box.
        List<Vet> vets = vetService.getAllVets();
        model.addAttribute("visit", visit);
        model.addAttribute("vets", vets);
        return "visit/add-visit";
    }

    @PostMapping("/saveVisit")
    public String saveVisit(@Valid @ModelAttribute("visit") Visit newVisit, BindingResult result){
        if(result.hasErrors()){
            return "visit/add-visit";
        }
        visitService.saveVisit(newVisit);
        return "redirect:/visit";
    }
}
