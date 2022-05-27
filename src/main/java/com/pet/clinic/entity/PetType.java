package com.pet.clinic.entity;

import com.pet.clinic.constant.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pet_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetType extends IdBaseEntity{

    @NotEmpty(message = ErrorMessage.PET_TYPE_IS_REQUIRED_ERROR_MESSAGE)
    @Size(max = 50, message = ErrorMessage.PET_TYPE_SIZE_LIMIT_ERROR_MESSAGE)
    @Column(name = "petType")
    private String petTypeName;

}