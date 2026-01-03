package com.nbu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PERSON")
@ToString(callSuper = true)
public class Person extends BaseEntity {

    private String firstName;

    private String middleName;

    private String lastName;

    private String ucn;

    private LocalDate birthDate;

}
