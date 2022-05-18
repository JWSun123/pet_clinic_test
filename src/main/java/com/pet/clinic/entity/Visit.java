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
public class Visit extends IdBaseEntity {

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
    @ManyToOne
    private Pet pet;

    @ManyToOne
    private Vet vet;

    public Visit() {
    }
}
