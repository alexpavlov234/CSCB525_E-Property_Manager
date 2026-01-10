package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @DecimalMin(value = "0.0", inclusive = false, message = "The amount must be greater than zero!")
    private BigDecimal amount;

    @NotNull(message = "Type must not be null!")
    @ManyToOne(fetch = FetchType.LAZY)
    private TaxType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

}
