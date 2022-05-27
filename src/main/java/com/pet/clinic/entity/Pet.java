package com.pet.clinic.entity;

import com.pet.clinic.constant.ErrorMessage;
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
public class Pet extends IdBaseEntity{

    @NotEmpty(message = ErrorMessage.PET_NAME_IS_REQUIRED_ERROR_MESSAGE)
    @Column(name = "pet_name")
    @Size(max=30, message = ErrorMessage.PET_NAME_SIZE_LIMIT_ERROR_MESSAGE)
    private String petName;

    @NotNull(message = ErrorMessage.DOB_IS_REQUIRED_ERROR_MESSAGE)
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @ManyToOne
    @JoinColumn(name = "pet_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}