package com.nbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne
    private Tax tax;

    private BigDecimal amount;

    private LocalDate paymentDate;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Building building;

}
