package com.pet.clinic.service;


import com.pet.clinic.entity.Owner;
import com.pet.clinic.entity.Pet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.OwnerRepository;
import com.pet.clinic.repository.PetRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    //constructor
    public OwnerService(OwnerRepository ownerRepository, PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    //read all owners for table
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    //save or update
    public void saveOrUpdateOwner (Owner newOwner) throws RecordNotFoundException {
        if (newOwner.getId() == null) {
            ownerRepository.save(newOwner);
        } else {
            Owner ownerFromDb = getOwnerById(newOwner.getId());
            ownerFromDb.setFirstName(newOwner.getFirstName());
            ownerFromDb.setLastName(newOwner.getLastName());
            ownerFromDb.setEmail(newOwner.getEmail());
            ownerFromDb.setTel(newOwner.getTel());
            ownerFromDb.setAddress(newOwner.getAddress());
            ownerRepository.save(ownerFromDb);
        }
    }

    //search by id
    public Owner getOwnerById(Long id) throws RecordNotFoundException{
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isPresent()) {
            return owner.get();
        }
        throw new RecordNotFoundException("There no client with this Id.");
    }

    //search by tel
    public Owner getOwnerByTel(String tel) throws RecordNotFoundException {
        //should be able to find with partial number
        List<Owner> allOwners = ownerRepository.findAll();
        //If we want to get results with similar tel
        // List<Owner> foundOwners = new ArrayList<>();
        for(Owner owner : allOwners) {
            if(owner.getTel().equals(tel)) {
                //foundOwners.add(owner);
                //return foundOwners;
                return owner;
            }
        }
        throw new RecordNotFoundException("There is no client with this phone number.");
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
}
