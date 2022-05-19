package com.pet.clinic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends IdBaseEntity{

    @NotEmpty
    @Column(name = "appointment_date")
    private Date appointmentDate;

    @Column(name = "description")
    @Size(max = 200)
    private String description;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
