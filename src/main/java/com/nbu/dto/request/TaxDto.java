package com.nbu.dto.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaxDto {
    private long id;

    @DecimalMin(value = "0.0", inclusive = false, message = "The amount must be greater than zero!")
    private BigDecimal amount;

    private long taxTypeId;

    private long apartmentId;

}
