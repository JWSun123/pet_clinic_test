package com.pet.clinic.service;

import com.pet.clinic.entity.Visit;
import com.pet.clinic.repository.VisitRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {
    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Visit> getAllVisit() {
        return visitRepository.findAll(Sort.by(Sort.Direction.ASC, "visitDate"));
    }

    public void saveVisit(Visit newVisit) {
        visitRepository.save(newVisit);
    }

    public List<Visit> getVisitByPetId(Long petId){
        return visitRepository.findVisitByPet(petId);
    }

    public List<Visit> getVisitByPet(String petName) {
        return visitRepository.findByPetName(petName);
    }
}
