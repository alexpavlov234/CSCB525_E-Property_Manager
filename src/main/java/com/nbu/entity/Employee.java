package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Employee extends BaseEntity {
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "manager")
    private Set<Building> buildings;
}
