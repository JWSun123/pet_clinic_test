package com.pet.clinic.service;

import com.pet.clinic.entity.Specialty;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public List<Specialty> getAllSpecialty() {
        return specialtyRepository.findAll();
    }

    public Specialty getSpecialtyById(Long id) throws RecordNotFoundException {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (specialty.isPresent()) {
            return specialty.get();
        }throw new RecordNotFoundException("Specialty Not Found");
    }

    public void saveOrUpdateSpecialty(Specialty newSpecialty) throws RecordNotFoundException {
        if (newSpecialty.getId() == null) {
            specialtyRepository.save(newSpecialty);
        }
        else{
            Specialty specialty = getSpecialtyById(newSpecialty.getId());
            specialty.setSpecialtyName(newSpecialty.getSpecialtyName());
            specialtyRepository.save(specialty);
        }
    }

    public void deleteSpecialtyById(Long id) throws RecordNotFoundException {
        getSpecialtyById(id);
        specialtyRepository.deleteById(id);
    }
}
