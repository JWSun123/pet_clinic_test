package com.pet.clinic.repository;

import com.pet.clinic.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {

    @Query(value = "select * from vets where vets.first_name like %:keyword% or vets.last_name like %:keyword% or vets.tel like %:keyword% or vets.email like %:keyword%", nativeQuery = true)
    List<Vet> findByKeyword(@Param("keyword") String keyword);

}
