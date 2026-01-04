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
    private  long id;
    private  long taxId;
    private  long taxApartmentId;
    private  long taxBuildingId;
    private  BigDecimal amount;
    private  LocalDate paymentDate;
    private  long companyId;
    private  long employeeId;
}