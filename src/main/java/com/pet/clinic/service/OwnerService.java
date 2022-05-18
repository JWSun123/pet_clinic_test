package com.pet.clinic.service;

import com.pet.clinic.entity.Owner;
import com.pet.clinic.entity.Person;
import com.pet.clinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
//    public List<Owner> getOwnerList(){
//
//    }
}
