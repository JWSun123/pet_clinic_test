package com.pet.clinic.service;

import com.pet.clinic.entity.Owner;
import com.pet.clinic.entity.Person;
import com.pet.clinic.entity.Pet;
import com.pet.clinic.entity.PetType;
import com.pet.clinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
//    public List<Owner> getOwnerList(){
//        Owner owner = new Owner();
//        owner.setFirstName("tt");
//        owner.setLastName("oo");
//        owner.setAddress("123, abc");
//        owner.setTel("123123123");
//
//        Pet pet = new Pet();
//        pet.setPetName("kkk");
//        pet.setDob(new Date(2000,12,12));
//        pet.setPetType(new PetType(1l, "CAT"));
//
//        Set<Pet> pets = new HashSet<>();
//        pets.add(pet);
//        owner.setPets(pets);
//
//        return null;
//    }
}
