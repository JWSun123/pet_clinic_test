package com.pet.clinic.entity;

import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.*;
import java.util.Date;

@Entity
@Table(name = "pets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet extends IdBaseEntity implements Serializable {

    @NotEmpty(message = "Pet name is required")
    @Column(name = "pet_name")
    @Size(max=30)
    private String petName;

    @NotNull(message = "You must select the date of birth")
    @Column(name = "date_of_birth")
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