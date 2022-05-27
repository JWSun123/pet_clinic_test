package com.pet.clinic.entity;

import com.pet.clinic.constant.ErrorMessage;
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

    @NotEmpty(message = ErrorMessage.DATE_IS_REQUIRED_ERROR_MESSAGE)
    @Column(name = "appointment_date")
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String appointmentDate;

    @Column(name = "description")
    @Size(max = 200, message = ErrorMessage.DESCRIPTION_SIZE_LIMIT_ERROR_MESSAGE)
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
