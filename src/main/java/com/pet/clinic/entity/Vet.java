package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vets")
@Getter
@Setter
public class Vet extends Person {

    @Column(name = "specialty_id")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "vet_specialties",
            joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private Set<Specialty> specialties;

}
