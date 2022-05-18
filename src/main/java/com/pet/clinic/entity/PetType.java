package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pet_types")
@Getter
@Setter
public class PetType extends IdBaseEntity{

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String petType;
}
