package com.nbu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyPaymentDto {
    private long companyId;

    @NotBlank(message = "Company name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Company name has to start with capital letter!")
    private String companyName;

    @Positive(message = "Total amount of payments must be a positive number!")
    private BigDecimal totalAmountOfPayments;

    @Positive(message = "Payment year must be a positive number!")
    private int paymentYear;
    @Positive(message = "Payment month must be a positive number!")
    private int paymentMonth;
}
