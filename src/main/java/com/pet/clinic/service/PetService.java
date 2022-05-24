package com.pet.clinic.service;

import com.pet.clinic.entity.Pet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findPetByKeyword(String keyword) throws RecordNotFoundException {
        List<Pet> pets = petRepository.findByKeyword(keyword);
        if(pets.size() != 0){
            return pets;
        }
        else{
            throw new RecordNotFoundException("Pet Not Found");
        }
    }
}
