package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Company extends BaseEntity {
    private String name;

    private String uic;

    private String vatNumber;

    private String registeredAddress;

    private String mailingAddress;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    private Set<Employee> employees;

}
