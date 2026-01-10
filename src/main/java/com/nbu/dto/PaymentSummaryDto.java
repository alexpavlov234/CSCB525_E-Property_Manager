package com.nbu.dto;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class PaymentSummaryDto {
    private final long entityId;
    private final String entityName;
    private final BigDecimal totalPaymentAmount;
    private final long paymentCount;

    public PaymentSummaryDto(long entityId, String entityName, Integer totalPaymentAmount, Long paymentCount) {
        this.entityId = entityId;
        this.entityName = entityName;
        this.totalPaymentAmount = totalPaymentAmount != null ? BigDecimal.valueOf(totalPaymentAmount) : BigDecimal.ZERO;
        this.paymentCount = paymentCount != null ? paymentCount : 0L;
    }

}

