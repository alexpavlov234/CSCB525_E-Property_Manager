package com.nbu.dto;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class TaxSummaryDto {
    private final long entityId;
    private final String entityName;
    private final BigDecimal totalTaxAmount;
    private final long taxCount;

    public TaxSummaryDto(long entityId, String entityName, Integer totalTaxAmount, Long taxCount) {
        this.entityId = entityId;
        this.entityName = entityName;
        this.totalTaxAmount = totalTaxAmount != null ? BigDecimal.valueOf(totalTaxAmount) : BigDecimal.ZERO;
        this.taxCount = taxCount != null ? taxCount : 0L;
    }
}

