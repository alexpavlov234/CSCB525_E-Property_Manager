package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tax_type")
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class TaxType extends BaseEntity {
    @NotBlank(message = "The type must not be blank!")
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Set<Tax> taxes;

}