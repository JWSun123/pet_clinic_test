package com.pet.clinic.entity;

import com.pet.clinic.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends IdBaseEntity{

    @NotEmpty(message = ErrorMessage.DATE_IS_REQUIRED_ERROR_MESSAGE)
    @Column(name = "appointment_date")
    private String appointmentDate;

    @Column(name = "description")
    @Size(max = 200, message = ErrorMessage.DESCRIPTION_SIZE_LIMIT_ERROR_MESSAGE)
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pet_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Pet pet;

}