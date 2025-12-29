package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "apartment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Apartment extends BaseEntity {

    private int number;

    private int floor;

    private BigDecimal area;

    @ManyToOne
    private Person owner;

    @ManyToOne
    private Building building;

    @OneToMany(mappedBy = "apartment")
    private Set<Person> residents;

    @OneToMany(mappedBy = "apartment")
    private Set<Pet> pets;

    @OneToMany(mappedBy = "apartment")
    private Set<Tax> taxes;
}
