package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;


@Entity
@Table(name = "visits")
@Getter
@Setter
public class Visit extends BaseEntity{

    @NotEmpty
    @Column(name = "visit_date")
    private DateTime visitDate;

    @NotEmpty
    @Column(name = "service")
    @OneToMany
    private Set<Service> services;

    @Column(name = "description")
    private String description;

    @NotEmpty
    @Column(name = "pet_id")
    @ManyToOne
    private Pet pet;

    @Column(name = "vet_id")
    @ManyToOne
    private Vet vet;

    public Visit() {
    }
}
