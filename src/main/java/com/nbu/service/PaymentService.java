package com.nbu.service;

import com.nbu.dao.PaymentDao;
import com.nbu.dto.PaymentDto;

import java.util.List;

public class PaymentService {
    public void createPayment(PaymentDto paymentDto) {
        if (PaymentDao.isPaymentForThisMonthMade(paymentDto.getTaxId(), paymentDto.getPaymentDate())) {
            throw new IllegalArgumentException("Payment for this month is already made.");
        }

        PaymentDao.createPayment(paymentDto);
    }

    public void updatePayment(PaymentDto paymentDto) {
        if (PaymentDao.isPaymentForThisMonthMade(paymentDto.getTaxId(), paymentDto.getPaymentDate())) {
            throw new IllegalArgumentException("Payment for this month is already made.");
        }
        PaymentDao.updatePayment(paymentDto.getId(),paymentDto);
    }

    public PaymentDto getPayment(long id) {
        return PaymentDao.getPayment(id);
    }

    public List<PaymentDto> getPayments() {
        return PaymentDao.getPayments();
    }

    public void deletePayment(long id) {
        PaymentDao.deletePayment(id);
    }
}
