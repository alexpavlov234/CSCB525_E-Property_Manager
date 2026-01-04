package com.nbu.service;
import com.nbu.dao.PaymentDao;
import com.nbu.dao.TaxDao;
import com.nbu.dto.PaymentDto;
import java.math.BigDecimal;
import java.util.List;
public class PaymentService {
    public void createPayment(PaymentDto paymentDto) {
        validatePaymentData(paymentDto);
        if (PaymentDao.isPaymentForThisMonthMade(paymentDto.getTaxId(), paymentDto.getPaymentDate())) {
            throw new IllegalArgumentException("Payment for this month is already made.");
        }
        PaymentDao.createPayment(paymentDto);
    }
    public void updatePayment(long id, PaymentDto paymentDto) {
        validatePaymentData(paymentDto);
        if (PaymentDao.isPaymentForThisMonthMadeExcludingId(paymentDto.getTaxId(), paymentDto.getPaymentDate(), id)) {
            throw new IllegalArgumentException("Another payment for this month already exists.");
        }
        PaymentDao.updatePayment(id, paymentDto);
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
    private void validatePaymentData(PaymentDto paymentDto) {
        if (!TaxDao.taxExists(paymentDto.getTaxId())) {
            throw new IllegalArgumentException("Tax with id " + paymentDto.getTaxId() + " does not exist.");
        }
        if (!PaymentDao.employeeExists(paymentDto.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with id " + paymentDto.getEmployeeId() + " does not exist.");
        }
        // Validate that payment amount matches the tax amount
        BigDecimal taxAmount = TaxDao.getTaxAmount(paymentDto.getTaxId());
        if (paymentDto.getAmount() == null || paymentDto.getAmount().compareTo(taxAmount) != 0) {
            throw new IllegalArgumentException("Payment amount must equal the tax amount: " + taxAmount);
        }
    }
}
