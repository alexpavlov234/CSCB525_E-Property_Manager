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
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentTaxDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long taxId;
    private long taxApartmentId;
    private long taxBuildingId;

    @DecimalMin(value = "0.0", inclusive = false, message = "The amount must be greater than zero!")
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Payment date cannot be null.")
    @PastOrPresent(message = "Payment date cannot be in the future.")
    private LocalDate paymentDate;

    private long companyId;
    private long employeeId;
}
