package com.nbu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class PaymentTaxDto implements Serializable {
    private final static long serialVersionUID = 1L;

    private final long id;
    private final long taxId;
    private final long taxApartmentId;
    private final long taxBuildingId;

    private final BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate paymentDate;

    private final long companyId;
    private final long employeeId;
}
