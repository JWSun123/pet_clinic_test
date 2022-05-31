package com.pet.clinic.service;

import com.pet.clinic.entity.Specialty;
import com.pet.clinic.entity.Vet;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.SpecialtyRepository;
import com.pet.clinic.repository.VetRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VetService {
    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;

    public VetService(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
    }

    public List<Vet> getAllVets() {
        return vetRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Vet getVetById(Long id) throws RecordNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        if (vet.isPresent()) {
            return vet.get();
        }
        throw new RecordNotFoundException("Vet Not Found");
    }

    public void saveOrUpdateVet(Vet newVet) throws RecordNotFoundException {
        if (newVet.getId() == null) {
            vetRepository.save(newVet);
        } else {
            Vet vetFromDb = getVetById(newVet.getId());
            vetFromDb.setEmail(newVet.getEmail());
            vetFromDb.setFirstName(newVet.getFirstName());
            vetFromDb.setLastName(newVet.getLastName());
            vetFromDb.setTel(newVet.getTel());
            vetRepository.save(vetFromDb);
        }
    }

    public Set<Specialty> getVetSpecialtiesByVetId(Long vetId) throws RecordNotFoundException {
        Vet vet = getVetById(vetId);
        return vet.getSpecialties();
    }

    public List<Specialty> getAllTheRestSpecialties(Long vetId) throws RecordNotFoundException {
        List<Specialty> allSpecialties = specialtyRepository.findAll();
        Set<Specialty> vetSpecialties = getVetSpecialtiesByVetId(vetId);
        List<Specialty> result = new ArrayList<>();
        for (Specialty specialty : allSpecialties) {
            if (!vetSpecialties.contains(specialty)) {
                result.add(specialty);
            }
        }
        return result;
    }

    public void addVetSpecialty(Long vetId, Specialty selectpecialty) throws RecordNotFoundException {
        Vet vet = getVetById(vetId);

        // add specialty to vet.
        Specialty specialty = specialtyRepository.findById(selectpecialty.getId()).get();
        vet.getSpecialties().add(specialty);
        vetRepository.save(vet);

        // add vet to specialty.
        specialty.getVets().add(vet);
        specialtyRepository.save(specialty);

    }

    public void deleteVetSpecialty(Long vetId, Long specialtyId) throws RecordNotFoundException {

        Vet vet = getVetById(vetId);
        Specialty specialty = specialtyRepository.getById(specialtyId);

        // remove specialty in vet.
        vet.getSpecialties().remove(specialty);
        vetRepository.save(vet);

        //remove vet in specialty.
        specialty.getVets().remove(vet);
        specialtyRepository.save(specialty);
    }

    public void deleteVet(Long vetId) {
        vetRepository.deleteById(vetId);
    }

    public List<Vet> searchVetByKeyword(String keyword) throws RecordNotFoundException {
        List<Vet> vets = vetRepository.findByKeyword(keyword);
        if(vets.size() != 0)
        return vets;
        else{
            throw  new RecordNotFoundException("Vet not found.");
        }

    }
}
