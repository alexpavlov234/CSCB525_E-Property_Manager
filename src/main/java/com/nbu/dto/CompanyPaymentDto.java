package com.nbu.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyPaymentDto {
    private long companyId;
    private String companyName;
    private BigDecimal totalAmountOfPayments;
    private int paymentYear;
    private int paymentMonth;
}
