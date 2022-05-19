package com.pet.clinic.entity;

import lombok.*;

import javax.persistence.*;
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
    private Date dob;

    @ManyToOne
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
