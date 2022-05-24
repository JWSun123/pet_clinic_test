package com.pet.clinic.service;

import com.pet.clinic.entity.Pet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.PetRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    public List<Pet> getAllPets() {
        return petRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Pet getPetById(Long id) throws RecordNotFoundException{
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()){
            return pet.get();
        }
        throw new RecordNotFoundException("Pet Not Found");
    }

    public void saveOrUpdatePet(Pet newPet) throws RecordNotFoundException{
        if (newPet.getId() == null){
            petRepository.save(newPet);
        }else{
            Pet petFromDb = getPetById(newPet.getId());
            petFromDb.setPetName(newPet.getPetName());
            petFromDb.setDob(newPet.getDob());
            petFromDb.setPetType(newPet.getPetType());
            petRepository.save(petFromDb);
        }
    }

    public void deletePet(Long petId){
        petRepository.deleteById(petId);
    }

}