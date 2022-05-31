package com.pet.clinic.entity;

import com.pet.clinic.constant.ErrorMessage;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "specialties")
@Getter
@Setter
public class Specialty extends IdBaseEntity {

    @NotEmpty(message = ErrorMessage.SPECIALTY_NAME_IS_REQUIRED_ERROR_MESSAGE)
    @Column(name = "specialty_name")
    @Size(max = 50, message = ErrorMessage.SPECIALTY_NAME_SIZE_LIMIT_ERROR_MESSAGE)
    private String specialtyName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "specialties")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Vet> vets;
}
