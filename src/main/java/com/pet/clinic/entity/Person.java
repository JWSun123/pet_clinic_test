package com.pet.clinic.entity;

import com.pet.clinic.constant.ErrorMessage;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends IdBaseEntity {

    @Column(name = "first_name")
    @NotEmpty(message = ErrorMessage.FIRSTNAME_IS_REQUIRED_ERROR_MESSAGE)
    @Size(max = 30, message = ErrorMessage.FIRSTNAME_SIZE_LIMIT_ERROR_MESSAGE)
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = ErrorMessage.LASTNAME_IS_REQUIRED_ERROR_MESSAGE)
    @Size(max = 30, message = ErrorMessage.LASTNAME_SIZE_LIMIT_ERROR_MESSAGE)
    private String lastName;

    @Column(name = "tel")
    @NotEmpty(message = ErrorMessage.TEL_IS_REQUIRED_ERROR_MESSAGE)
    @Size(max = 15, message = ErrorMessage.TEL_SIZE_LIMIT_ERROR_MESSAGE)
    private String tel;


    @Column(name = "email")
    @Email(message = ErrorMessage.EMAIL_INVALID_ERROR_MESSAGE)
    @NotEmpty(message = ErrorMessage.EMAIL_EMPTY_ERROR_MESSAGE)
    private String email;

}
