package com.pet.clinic.repository;

import com.pet.clinic.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query(value = "select * from visits where pet_id = :petId",nativeQuery = true)
    List<Visit> findVisitByPet(Long petId);

    @Query(value = "select * from visits join pets on visits.pet_id = pets.id where pets.pet_name like %:petName%", nativeQuery = true)
    List<Visit> findByPetName(String petName);
}
