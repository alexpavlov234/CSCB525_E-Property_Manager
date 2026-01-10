package com.nbu.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class CompanyPaymentDto {
    private final long companyId;
    private final String companyName;
    private final BigDecimal totalAmountOfPayments;
    private final int paymentYear;
    private final int paymentMonth;
}
