package com.nbu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto implements Serializable {
    private  Long id;
    private  Long taxId;
    private  Long taxApartmentId;
    private  Long taxBuildingId;
    private  BigDecimal amount;
    private  LocalDate paymentDate;
    private  Long companyId;
    private  Long employeeId;
}