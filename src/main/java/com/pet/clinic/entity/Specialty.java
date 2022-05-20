package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "specialties")
@Getter
@Setter
public class Specialty extends IdBaseEntity {

    @NotEmpty
    @Column(name = "specialty_name")
    @Size(max = 50)
    private String specialtyName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "specialties")
    private Set<Vet> vets;
}
