package com.pet.clinic.entity;

import com.pet.clinic.constant.ErrorMessage;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
public class Owner extends Person{

    @Column(name = "address")
    @Size(max = 200, message = ErrorMessage.ADDRESS_SIZE_LIMITE_ERROR_MESSAGE)
    @NotEmpty(message = ErrorMessage.ADDRESS_IS_REQUIRED_ERROR_MESSAGE)
    private String address;

    @OneToMany(mappedBy = "owner")
    private List<Pet> pet;
}

