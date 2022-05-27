package com.pet.clinic.repository;

import com.pet.clinic.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query(value = "select * from pets join owners on owners.id = pets.owner_id join pet_types pt on pt.id = pets.pet_type_id where pet_name like %:keyword% or tel like %:keyword% or email like %:keyword% or pet_type like %:keyword%", nativeQuery = true)
    List<Pet> findByKeyword(@Param("keyword") String keyword);

}