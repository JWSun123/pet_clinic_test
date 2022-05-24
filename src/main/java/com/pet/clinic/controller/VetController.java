package com.pet.clinic.controller;

import com.pet.clinic.entity.Owner;
import com.pet.clinic.entity.Specialty;
import com.pet.clinic.entity.Vet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    // home page for Vet, show all a table of all vets.
    @GetMapping("/vet")
    public String getAllVets(Model model) {
        List<Vet> vetList = vetService.getAllVets();
        model.addAttribute("vets", vetList);
        return "vet-list";
    }

    // when clicking on add a vet. show a form for adding a new vet.
    @GetMapping("/addVet")
    public String addVet(Model model) {
        Vet vet = new Vet();
        model.addAttribute("vet", vet);
        return "add-vet";
    }

    // saving the new vet. won't open any new page.
    @PostMapping("/saveVet")
    public String saveVet(@Valid @ModelAttribute("vet") Vet newVet, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "/add-vet";
        }
        vetService.saveOrUpdateVet(newVet);
        return "redirect:/vet";
    }

    // when clicking on the modify button beside a vet. show a form for update vet info(not include specialty)
    // a separate button for view/modify vet's specialty.
    @GetMapping("/showVetInfoForUpdate/{vetId}")
    public String showVetInfoForUpdate(@PathVariable(value = "vetId") Long vetId, Model model) throws RecordNotFoundException {
        Vet vet = vetService.getVetById(vetId);
        model.addAttribute("vet", vet);
        return "update-vet-info";
    }

    //updating info, won't open any new page.
    @PostMapping("/updateVetInfo")
    public String updateVetInfo(@Valid @ModelAttribute("vet") Vet updateVet, BindingResult result) throws RecordNotFoundException {
        if (result.hasErrors()) {
            return "/update-vet-info";
        }
        vetService.saveOrUpdateVet(updateVet);
        return "redirect:/vet";
    }

    // when click the separate button for specialty. it shows a table for vet's specialty.
    // show add/delete button for specialty. add button with a select box. the content will be allRestSpecialty.
    // the specialties the vet already has, won't be shown in the select box for adding.
    @GetMapping("/showVetSpecialty/{vetId}")
    public String showVetSpecialty(@PathVariable(value = "vetId") Long vetId, Model model, @ModelAttribute("specialty") Specialty selecteSpecialty) throws RecordNotFoundException {

        Set<Specialty> vetSpecialties = vetService.getVetSpecialtiesByVetId(vetId);
        List<Specialty> allRestSpecialties = vetService.getAllTheRestSpecialties(vetId);

        model.addAttribute("vetSpecialties", vetSpecialties);
        model.addAttribute("allRestSpecialties", allRestSpecialties);
        model.addAttribute("vetId", vetId);

        return "update-vet-specialty";
    }

    // when select a specialty from the select box and click add button. won't open any new page. stays on the current page.
    @PostMapping("/showVetSpecialty/{vetId}/addVetSpecialty")
    public String addVetSpecialty(@PathVariable(value = "vetId") Long vetId, @ModelAttribute("specialty") Specialty selectSpecialty) throws RecordNotFoundException {
        vetService.addVetSpecialty(vetId, selectSpecialty);
        return "redirect:/showVetSpecialty/{vetId}";
    }

    // when clicking delete specialty. redirect to the show vet specialty page.
    @GetMapping("/showVetSpecialty/{vetId}/deleteVetSpecialty/{specialtyId}")
    public String deleteVetSpecialty(@PathVariable(value = "vetId") Long vetId, @PathVariable(value = "specialtyId") Long specialtyId) throws RecordNotFoundException {
        vetService.deleteVetSpecialty(vetId, specialtyId);
        return "redirect:../";
    }

    // when clicking delete vet. redirect to the vet page.
    @GetMapping("/deleteVet/{vetId}")
    public String deleteVet(@PathVariable(value = "vetId") Long vetId) {
        vetService.deleteVet(vetId);
        return "redirect:../vet";
    }

    @GetMapping("/searchVet")
    public String searchVet(Model model, @RequestParam("keyword") String keyword) throws RecordNotFoundException {

        List<Vet> vets;
        if (keyword != null) {
            vets = vetService.searchVetByKeyword(keyword);
        } else {
            vets = vetService.getAllVets();
        }
        model.addAttribute("vets", vets);
        return "vet-list";
    }
}
