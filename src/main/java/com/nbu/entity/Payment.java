package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Payment extends BaseEntity {

    @NotNull(message = "Tax cannot be null.")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tax tax;

    @NotNull(message = "Amount cannot be null.")
    @Positive(message = "Amount must be positive.")
    private BigDecimal amount;

    @NotNull(message = "Payment date cannot be null.")
    @PastOrPresent(message = "Payment date cannot be in the future.")
    private LocalDate paymentDate;

    @NotNull(message = "Employee cannot be null.")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;


}
