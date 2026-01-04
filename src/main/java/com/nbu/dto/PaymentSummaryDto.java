package com.nbu.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentSummaryDto {
    private long entityId;
    private String entityName;
    private BigDecimal totalPaymentAmount;
    private long paymentCount;

    public PaymentSummaryDto(long entityId, String entityName, Integer totalPaymentAmount, Long paymentCount) {
        this.entityId = entityId;
        this.entityName = entityName;
        this.totalPaymentAmount = totalPaymentAmount != null ? BigDecimal.valueOf(totalPaymentAmount) : BigDecimal.ZERO;
        this.paymentCount = paymentCount != null ? paymentCount : 0L;
    }

}

