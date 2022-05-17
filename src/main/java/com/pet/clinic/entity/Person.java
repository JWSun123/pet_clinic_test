package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Getter
@Setter
public class Person extends BaseEntity{

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Column(name = "tel")
    @NotEmpty
    private String tel;

    @Column(name = "address")
    private String address;

}
