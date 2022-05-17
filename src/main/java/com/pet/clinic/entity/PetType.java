package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "pet_types")
@Getter
@Setter
public class PetType extends BaseEntity{
    @NotEmpty
    @Column(name = "pet_type")
    private String petType;
}
