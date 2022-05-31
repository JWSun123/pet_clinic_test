package com.pet.clinic.service;


import com.pet.clinic.entity.Owner;
import com.pet.clinic.entity.Pet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.OwnerRepository;
import com.pet.clinic.repository.PetRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public OwnerService(OwnerRepository ownerRepository, PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    //Fetch all owners and sort them by their id
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    //save or update owner
    public void saveOrUpdateOwner (Owner newOwner, List<Pet> petList) throws RecordNotFoundException {
        //If the owner doesn't exist, save the new owner and new pet
        if (newOwner.getId() == null) {
            newOwner.setFirstName(newOwner.getFirstName());
            newOwner.setLastName(newOwner.getLastName());
            newOwner.setTel(newOwner.getTel());
            newOwner.setAddress(newOwner.getAddress());
            newOwner.setEmail(newOwner.getEmail());
            newOwner.setPet(petList);

            ownerRepository.save(newOwner);

            for (Pet pet: petList) {
                pet.setOwner(newOwner);
                petRepository.save(pet);
            }
        } else {
            //The owner already exists, update their information
            Owner ownerFromDb = getOwnerById(newOwner.getId());
            ownerFromDb.setFirstName(newOwner.getFirstName());
            ownerFromDb.setLastName(newOwner.getLastName());
            ownerFromDb.setEmail(newOwner.getEmail());
            ownerFromDb.setTel(newOwner.getTel());
            ownerFromDb.setAddress(newOwner.getAddress());

            //Get existing pet list
            List<Pet> previousPets=ownerFromDb.getPet();

            //Add existing pets to toUpdate list
            List<Pet> toUpdate = new ArrayList<>();
            toUpdate.addAll(petList);
            toUpdate.retainAll(previousPets);

            //New pets will be added to toAdd list
            List<Pet> toAdd = new ArrayList<>();
            toAdd.addAll(petList);
            toAdd.removeAll(toUpdate);

            //Update the toUpdate pets' information
            for (Pet pet : toUpdate) {
                for (Pet other: petList) {
                    if (other.getId() == pet.getId()) {
                        pet.setId(pet.getId());
                        pet.setPetName(other.getPetName());
                        pet.setDob(other.getDob());
                        pet.setPetType(other.getPetType());
                        pet.setOwner(ownerFromDb);
                    }
                }
            }

            //List to hold the all the modified and new pets
            List<Pet> updatedPetList = new ArrayList<>(toUpdate);

            for (Pet pet: toAdd) {
                pet.setOwner(ownerFromDb);
                updatedPetList.add(pet);
            }

            List<Pet> allPets = new ArrayList<>(updatedPetList);

            ownerFromDb.setPet(allPets);
            ownerRepository.save(ownerFromDb);

            //Saves the collection or else it gives validation errors
            petRepository.saveAll(updatedPetList);
        }
    }

    //Fetch owner by their id
    public Owner getOwnerById(Long id) throws RecordNotFoundException{
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isPresent()) {
            return owner.get();
        }
        throw new RecordNotFoundException("There no client with this Id.");
    }

    //fetch owner or pet by a keyword
    public List<Owner> getOwnerByKeyword(String keyword) {
        return ownerRepository.findByKeyword(keyword);
    }

    //delete the owner by id
    public void deleteOwnerById(Long id) {
        ownerRepository.deleteById(id);
    }

    //fetch owner by keyword
    public List<Owner> findOwnerByKeyword(String keyword){

        return ownerRepository.searchByKeyword(keyword);
    }
}