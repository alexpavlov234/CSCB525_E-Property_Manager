package com.nbu.entity;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Resident owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Building building;

    @OneToMany(mappedBy = "apartment")
    private Set<Resident> residents;

    @OneToMany(mappedBy = "apartment")
    private Set<Pet> pets;

    @OneToMany(mappedBy = "apartment")
    private Set<Tax> taxes;
}
