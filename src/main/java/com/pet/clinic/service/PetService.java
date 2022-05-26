package com.pet.clinic.service;

import com.pet.clinic.entity.*;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetService(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<Pet> findPetByKeyword(String keyword) {
        return petRepository.findByKeyword(keyword);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Pet getPetById(Long id) throws RecordNotFoundException {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()) {
            return pet.get();
        }
        throw new RecordNotFoundException("Pet Not Found");
    }

    public void saveOrUpdatePet(Pet newPet, Owner owner) throws RecordNotFoundException {
        if (newPet.getId() == null) {
            newPet.setOwner(owner);
            petRepository.save(newPet);
        } else {
            Pet petFromDb = getPetById(newPet.getId());
            petFromDb.setPetName(newPet.getPetName());
            petFromDb.setDob(newPet.getDob());
            petFromDb.setPetType(newPet.getPetType());
            petRepository.save(petFromDb);
        }
    }

    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }
}