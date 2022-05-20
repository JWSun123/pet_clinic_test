package com.pet.clinic.repository;

import com.pet.clinic.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {
}