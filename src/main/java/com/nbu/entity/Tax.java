package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tax")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Tax extends BaseEntity {

    private BigDecimal amount;

    private String type;

    @ManyToOne
    private Apartment apartment;

}
