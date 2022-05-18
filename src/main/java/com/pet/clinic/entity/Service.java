package com.pet.clinic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "services")
@Getter
@Setter
public class Service extends IdBaseEntity {


    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = "service_name")
    private String serviceName;

}
