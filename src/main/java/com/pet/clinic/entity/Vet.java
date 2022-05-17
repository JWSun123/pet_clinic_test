package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "vets")
@Getter
@Setter
public class Vet extends Person{

    @Column(name = "service_id")
    @OneToMany
    private Set<Service> services;

}
