package com.pet.clinic.entity;

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
    @NotEmpty
    @Size(max = 30)
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @Size(max = 30)
    private String lastName;

    @Column(name = "tel")
    @NotEmpty
    @Size(max = 15)
    private String tel;


    @Column(name = "email")
    @Email
    private String email;

}
