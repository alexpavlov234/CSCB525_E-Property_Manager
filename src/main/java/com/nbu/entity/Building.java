package com.nbu.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Address cannot be blank!")
    private String address;

    @Min(value = 1, message = "Number of floors must be at least 1!")
    private int numberOfFloors;

    @Min(value = 1, message = "Number of apartments must be at least 1!")
    private int numberOfApartments;

    @DecimalMin(value = "0.0", inclusive = false, message = "Built-up area must be greater than 0!")
    private double builtUpArea;

    @DecimalMin(value = "0.0", inclusive = true, message = "Common areas must be at least 0!")
    private double commonAreas;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee manager;

    @OneToMany(mappedBy = "building")
    private Set<Apartment> apartments;

}
