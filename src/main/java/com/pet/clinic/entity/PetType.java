package com.pet.clinic.entity;

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

    @NotEmpty
    @Size(max = 50)
    private String petType;

}
