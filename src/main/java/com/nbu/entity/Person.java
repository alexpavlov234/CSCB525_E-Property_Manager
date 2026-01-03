package com.nbu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Person extends BaseEntity {

    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDate birthDate;

    private boolean useElevator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    @OneToMany(mappedBy = "owner")
    private Set<Apartment> ownedApartments;
}
