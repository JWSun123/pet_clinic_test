package com.pet.clinic.service;

import com.pet.clinic.entity.*;
import com.pet.clinic.exception.*;
import com.pet.clinic.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class PetTypeService {
    private final PetTypeRepository petTypeRepository;

    public PetTypeService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    public List<PetType> getAllPetType(){
        return petTypeRepository.findAll();
    }

    public PetType getPetTypeById(Long id) throws RecordNotFoundException {
        Optional<PetType> petType = petTypeRepository.findById(id);
        if(petType.isPresent()) {
            return petType.get();
        }
        throw new RecordNotFoundException("Pet type Not Found");
    }

    public void saveOrUpdatePetType(PetType newPetType) throws RecordNotFoundException {
        if(newPetType.getId() == null){
            petTypeRepository.save(newPetType);
        }else{
            PetType petType = getPetTypeById(newPetType.getId());
            petType.setPetTypeName(newPetType.getPetTypeName());
            petTypeRepository.save(petType);
        }
    }

    public void deletePetTypeById(Long id) {
        petTypeRepository.deleteById(id);
    }
}