package com.pet.clinic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotEmpty(message = "Please choose a date.")
//    @Size(min=1, message = "Please choose a date.")
    @Column(name = "appointment_date")
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
//    @DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private String appointmentDate;

    @Column(name = "description")
    @Size(max = 200)
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
