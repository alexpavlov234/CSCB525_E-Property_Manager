package com.nbu.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaxSummaryDto {
    private long entityId;
    private String entityName;
    private BigDecimal totalTaxAmount;
    private long taxCount;

    public TaxSummaryDto(long entityId, String entityName, Integer totalTaxAmount, Long taxCount) {
        this.entityId = entityId;
        this.entityName = entityName;
        this.totalTaxAmount = totalTaxAmount != null ? BigDecimal.valueOf(totalTaxAmount) : BigDecimal.ZERO;
        this.taxCount = taxCount != null ? taxCount : 0L;
    }
}

