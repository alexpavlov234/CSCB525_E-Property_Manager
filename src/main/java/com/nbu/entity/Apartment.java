package com.nbu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Min(value = 1, message = "Apartment number must be positive!")
    private int number;

    @Min(value = 0, message = "Floor must be zero or positive!")
    private int floor;

    @Positive(message = "Area must be positive!")
    private double area;

    @NotNull(message = "Owner must not be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    private Resident owner;

    @NotNull(message = "Building must not be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    private Building building;

    @OneToMany(mappedBy = "apartment")
    private Set<Resident> residents;

    @OneToMany(mappedBy = "apartment")
    private Set<Pet> pets;

    @OneToMany(mappedBy = "apartment")
    private Set<Tax> taxes;
}
