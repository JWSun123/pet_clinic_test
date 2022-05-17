package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "owners")
@Getter
@Setter
public class Owner extends Person{
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Pet> pets;
}
