package com.nbu.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaxDto {
    private long id;

    private BigDecimal amount;

    private String type;

    private long apartmentId;

}
