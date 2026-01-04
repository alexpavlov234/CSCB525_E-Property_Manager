package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.PaymentDto;
import com.nbu.dto.PaymentTaxDto;
import com.nbu.entity.Employee;
import com.nbu.entity.Payment;
import com.nbu.entity.Tax;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class PaymentDao {
    public static void createPayment(PaymentDto paymentDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Tax taxEntity = session.find(Tax.class, paymentDto.getTaxId());

            Employee employee = session.find(Employee.class, paymentDto.getEmployeeId());

            Payment paymentEntity = new Payment();
            paymentEntity.setTax(taxEntity);
            paymentEntity.setAmount(paymentDto.getAmount());
            paymentEntity.setPaymentDate(paymentDto.getPaymentDate());
            paymentEntity.setEmployee(employee);
            session.persist(paymentEntity);
            transaction.commit();

        }
    }
    public static PaymentTaxDto getPaymentTax(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PaymentTaxDto> criteriaQuery = criteriaBuilder.createQuery(PaymentTaxDto.class);
            Root<Payment> root = criteriaQuery.from(Payment.class);

            var taxFetch = root.fetch("tax");
            var aparmtentFetch = taxFetch.fetch("apartment");
            aparmtentFetch.fetch("building");
            var employeeFetch = root.fetch("employee");
            employeeFetch.fetch("company");

            criteriaQuery.select(
                    criteriaBuilder.construct(PaymentTaxDto.class,
                            root.get("id"),
                            root.get("tax").get("id"),
                            root.get("tax").get("apartment").get("id"),
                            root.get("tax").get("apartment").get("building").get("id"),
                            root.get("amount"),
                            root.get("paymentDate"),
                            root.get("employee").get("company").get("id"),
                            root.get("employee").get("id"))
            ).where(criteriaBuilder.equal(root.get("id"), id));

            Query<PaymentTaxDto> query = session.createQuery(criteriaQuery);
            return query.getSingleResult();
        }
    }

    public static List<PaymentDto> getPayments() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PaymentDto> criteriaQuery = criteriaBuilder.createQuery(PaymentDto.class);
            Root<Payment> root = criteriaQuery.from(Payment.class);

            var taxFetch = root.fetch("tax");
            var aparmtentFetch = taxFetch.fetch("apartment");
            aparmtentFetch.fetch("building");
            var employeeFetch = root.fetch("employee");
            employeeFetch.fetch("company");

            criteriaQuery.select(
                    criteriaBuilder.construct(PaymentDto.class,
                            root.get("id"),
                            root.get("tax").get("id"),
                            root.get("amount"),
                            root.get("paymentDate"),
                            root.get("employee").get("id"))
            );

            Query<PaymentDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }
    public static PaymentDto getPayment(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PaymentDto> criteriaQuery = criteriaBuilder.createQuery(PaymentDto.class);
            Root<Payment> root = criteriaQuery.from(Payment.class);

            var taxFetch = root.fetch("tax");
            var employeeFetch = root.fetch("employee");

            criteriaQuery.select(
                    criteriaBuilder.construct(PaymentDto.class,
                            root.get("id"),
                            root.get("tax").get("id"),
                            root.get("amount"),
                            root.get("paymentDate"),
                            root.get("employee").get("id"))
            ).where(criteriaBuilder.equal(root.get("id"), id));

            Query<PaymentDto> query = session.createQuery(criteriaQuery);
            return query.getSingleResult();
        }
    }

    public static List<PaymentTaxDto> getPaymentsTaxes() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PaymentTaxDto> criteriaQuery = criteriaBuilder.createQuery(PaymentTaxDto.class);
            Root<Payment> root = criteriaQuery.from(Payment.class);

            var taxFetch = root.fetch("tax");
            var aparmtentFetch = taxFetch.fetch("apartment");
            aparmtentFetch.fetch("building");
            var employeeFetch = root.fetch("employee");
            employeeFetch.fetch("company");

            criteriaQuery.select(
                    criteriaBuilder.construct(PaymentTaxDto.class,
                            root.get("id"),
                            root.get("tax").get("id"),
                            root.get("tax").get("apartment").get("id"),
                            root.get("tax").get("apartment").get("building").get("id"),
                            root.get("amount"),
                            root.get("paymentDate"),
                            root.get("employee").get("company").get("id"),
                            root.get("employee").get("id"))
            );

            Query<PaymentTaxDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static void updatePayment(long id, PaymentDto paymentDto){
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Tax taxEntity = session.find(Tax.class, paymentDto.getTaxId());

            Employee employee = session.find(Employee.class, paymentDto.getEmployeeId());

            Payment paymentEntity = session.find(Payment.class, id);
            paymentEntity.setTax(taxEntity);
            paymentEntity.setAmount(paymentDto.getAmount());
            paymentEntity.setPaymentDate(paymentDto.getPaymentDate());
            paymentEntity.setEmployee(employee);
            session.persist(paymentEntity);
            transaction.commit();
        }
    }

    public static void deletePayment(long id){
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Payment payment = session.find(Payment.class, id);
            session.remove(payment);
            transaction.commit();
        }
    }

    public static boolean isPaymentForThisMonthMade(long taxId, LocalDate paymentDate) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery(
                    "SELECT COUNT(p) FROM Payment p WHERE p.tax.id = :taxId " +
                            "AND MONTH(p.paymentDate) = :month " +
                            "AND YEAR(p.paymentDate) = :year", long.class)
                    .setParameter("taxId", taxId)
                    .setParameter("month", paymentDate.getMonthValue())
                    .setParameter("year", paymentDate.getYear())
                    .getSingleResult();

            return count > 0;
        }
    }

    public static boolean isPaymentForThisMonthMadeExcludingId(long taxId, LocalDate paymentDate, long excludePaymentId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery(
                    "SELECT COUNT(p) FROM Payment p WHERE p.tax.id = :taxId " +
                            "AND MONTH(p.paymentDate) = :month " +
                            "AND YEAR(p.paymentDate) = :year " +
                            "AND p.id != :excludeId", long.class)
                    .setParameter("taxId", taxId)
                    .setParameter("month", paymentDate.getMonthValue())
                    .setParameter("year", paymentDate.getYear())
                    .setParameter("excludeId", excludePaymentId)
                    .getSingleResult();

            return count > 0;
        }
    }

    public static boolean employeeExists(long employeeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Employee employee = session.find(Employee.class, employeeId);
            return employee != null;
        }
    }
}
