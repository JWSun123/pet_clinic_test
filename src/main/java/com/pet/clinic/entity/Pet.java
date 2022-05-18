package com.pet.clinic.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "pets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet extends IdBaseEntity {

    @NotEmpty
    @Column(name = "pet_name")
    @Size(max=30)

    @Pattern(regexp = "")
    private String petName;

//    @Column(name = "dob")
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
//    @DateTimeFormat(pattern = "yyyy/MM/dd")
//    private DateTime dob;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @ManyToOne
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Visit> visits;

}
