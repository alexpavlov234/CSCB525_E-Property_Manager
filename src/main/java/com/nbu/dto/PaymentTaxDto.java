package com.nbu.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentTaxDto {
    private  long id;
    private  long taxId;
    private  long taxApartmentId;
    private  long taxBuildingId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private  long companyId;
    private  long employeeId;
}
