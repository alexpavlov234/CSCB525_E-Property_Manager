package com.nbu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "resident")
@Getter
@Setter
@ToString
@DiscriminatorValue("RESIDENT")
public class Resident extends Person {

    private boolean useElevator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    @OneToMany(mappedBy = "owner")
    private Set<Apartment> ownedApartments;
}
