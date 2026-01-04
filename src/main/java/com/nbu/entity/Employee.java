package com.nbu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@DiscriminatorValue("EMPLOYEE")
@ToString
public class Employee extends Person {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Company must not be null!")
    private Company company;

    @OneToMany(mappedBy = "manager")
    private Set<Building> buildings;

    @OneToMany(mappedBy = "employee")
    private Set<Payment> payments;
}
