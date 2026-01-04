package com.nbu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    private long companyId;
    private long employeeId;
}
