package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;


@Entity
@Table(name = "building")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Building extends BaseEntity {

    private String address;

    private int numberOfFloors;

    private int numberOfApartments;

    private BigDecimal builtUpArea;

    private BigDecimal commonAreas;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "building")
    private Set<Apartment> apartments;

}
