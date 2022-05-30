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

    //read all owners for table
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    //save or update owner
    public void saveOrUpdateOwner (Owner newOwner, List<Pet> petList) throws RecordNotFoundException {
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
            Owner ownerFromDb = getOwnerById(newOwner.getId());
            ownerFromDb.setFirstName(newOwner.getFirstName());
            ownerFromDb.setLastName(newOwner.getLastName());
            ownerFromDb.setEmail(newOwner.getEmail());
            ownerFromDb.setTel(newOwner.getTel());
            ownerFromDb.setAddress(newOwner.getAddress());

            //Get existing pet list
            List<Pet> previousPets=ownerFromDb.getPet();

            //Add or update pet based on pet id (I've overridden the equals())
            List<Pet> toUpdate = new ArrayList<>();
            toUpdate.addAll(petList);
            toUpdate.retainAll(previousPets);

            //Pets that don't have the same id as previousPets will be pets to add
            List<Pet> toAdd = new ArrayList<>();
            toAdd.addAll(petList);
            toAdd.removeAll(toUpdate);

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

            //TODO: To check..Seems redundant but if I remove it to keep updatedPetList only, it doesn't update the pet
            List<Pet> allPets = new ArrayList<>(updatedPetList);

            ownerFromDb.setPet(allPets);
            ownerRepository.save(ownerFromDb);

            //pet.setOwner(newOwner);

            //Saves the collection or else it gives validation errors
            petRepository.saveAll(updatedPetList);
        }
    }

    public Owner getOwnerById(Long id) throws RecordNotFoundException{
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isPresent()) {
            return owner.get();
        }
        throw new RecordNotFoundException("There no client with this Id.");
    }

    public List<Owner> getOwnerByKeyword(String keyword) throws RecordNotFoundException {
        return ownerRepository.findByKeyword(keyword);
    }

    //get a list of all pets of owner by owner's id.
   /* public List<Pet> getPetByOwnerId(Long id) throws RecordNotFoundException {
        List<Pet> allPets = petRepository.findAll();
        List<Pet> ownerPets = new ArrayList<>();
        Owner owner = getOwnerById(id);
        for (Pet pet : allPets) {
            if (pet.getOwner().equals(owner)) {
                ownerPets.add(pet);
            }
        }
        return ownerPets;
    }*/

    //delete the owner by id
    public void deleteOwnerById(Long id) {
        ownerRepository.deleteById(id);
    }

    //String of pets for all owners
    /*public String nameAllPets() throws RecordNotFoundException {
        List<Owner> owners = ownerRepository.findAll();
        ArrayList<String> nameList = new ArrayList<>();
        for(Owner owner : owners) {
            nameList.add(owner);
        }
        String stringOfNames = String.join(",", nameList);
        return stringOfNames;
    }*/

//    //Save pet with owner
//    public void savePetByOwnerId(List<Pet> pet, Owner owner) throws RecordNotFoundException {
//        owner.setPet(pet);
//        saveOrUpdateOwner(owner);
//    }

    public List<Owner> findOwnerByKeyword(String keyword){

        return ownerRepository.searchByKeyword(keyword);
    }

    public void saveAllPets(List<Pet> petList) {
            petRepository.saveAll(petList);
    }
}