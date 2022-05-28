package com.pet.clinic.repository;

import com.pet.clinic.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    /*@Query(value = "select * from owners where owners.first_name like %:keyword% or owners.last_name like %:keyword% or owners.tel like %:keyword% or owners.email like %:keyword%", nativeQuery = true)*/

    @Query(value = "select * from owners join pets on owners.id = pets.owner_id where first_name like %:keyword% or last_name like %:keyword% or tel like %:keyword% or address like %:keyword% or pet_name like %:keyword%", nativeQuery = true)
    List<Owner> findByKeyword(@Param("keyword") String keyword);
}