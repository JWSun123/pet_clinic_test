package com.pet.clinic.entity;

import com.pet.clinic.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visit extends IdBaseEntity {

    @NotEmpty(message = ErrorMessage.VISIT_DATE_IS_REQUIRED_ERROR_MESSAGE)
    @Column(name = "visit_date")
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String visitDate;

    @Column(name = "description")
    @Size(max = 200, message = ErrorMessage.DESCRIPTION_SIZE_LIMIT_ERROR_MESSAGE)
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

    /*@OneToOne(mappedBy = "visit")
    private Appointment appointment;*/

}
