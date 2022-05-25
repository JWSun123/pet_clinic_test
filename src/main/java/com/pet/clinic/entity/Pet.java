package com.pet.clinic.entity;

import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "pets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet extends IdBaseEntity {

    @NotEmpty
    @Column(name = "pet_name")
    @Size(max=30)
    private String petName;

    @NotEmpty
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    @ManyToOne
    @JoinColumn(name = "pet_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}