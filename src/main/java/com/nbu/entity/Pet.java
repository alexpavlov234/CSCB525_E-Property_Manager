package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "pet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Pet extends BaseEntity {

    @NotBlank(message = "Pet name must not be blank!")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;
}
