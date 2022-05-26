package com.pet.clinic.service;

import com.pet.clinic.entity.Visit;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.VisitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitService {
    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Visit> getAllVisit() {
        return visitRepository.findAll(Sort.by(Sort.Direction.ASC, "visitDate"));
    }

    public Visit getVisitById(Long visitId) throws RecordNotFoundException {
        Optional<Visit> visit = visitRepository.findById(visitId);
        if(visit.isPresent()){
            return visit.get();
        }
        throw new RecordNotFoundException("Visit Not Found");
    }

    public void saveVisit(Visit newVisit) {
        visitRepository.save(newVisit);
    }

    public List<Visit> getVisitByPetId(Long petId){
        return visitRepository.findVisitByPet(petId);
    }
}
