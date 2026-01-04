package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.CompanyDto;
import com.nbu.dto.CompanyPaymentDto;
import com.nbu.entity.Company;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CompanyDao {

    public static void createCompany(CompanyDto companyDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Company companyEntity = new Company();

            companyEntity.setName(companyDto.getName());
            companyEntity.setUic(companyDto.getUic());
            companyEntity.setVatNumber(companyDto.getVatNumber());
            companyEntity.setRegisteredAddress(companyDto.getRegisteredAddress());
            companyEntity.setMailingAddress(companyDto.getMailingAddress());

            session.persist(companyEntity);
            transaction.commit();
        }
    }


    public static List<CompanyDto> getCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CompanyDto> criteriaQuery = criteriaBuilder.createQuery(CompanyDto.class);
            Root<Company> root = criteriaQuery.from(Company.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(
                            CompanyDto.class,
                            root.get("id"),
                            root.get("name"),
                            root.get("uic"),
                            root.get("vatNumber"),
                            root.get("registeredAddress"),
                            root.get("mailingAddress")
                    ));

            Query<CompanyDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static CompanyDto getCompany(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Company companyEntity = session.find(Company.class, id);
            return new CompanyDto(companyEntity.getId(), companyEntity.getName(), companyEntity.getUic(), companyEntity.getVatNumber(), companyEntity.getRegisteredAddress(), companyEntity.getMailingAddress());
        }
    }

    public static void updateCompany(long id, CompanyDto companyDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Company companyEntity = session.find(Company.class, id);
            companyEntity.setName(companyDto.getName());
            companyEntity.setUic(companyDto.getUic());
            companyEntity.setVatNumber(companyDto.getVatNumber());
            companyEntity.setRegisteredAddress(companyDto.getRegisteredAddress());
            companyEntity.setMailingAddress(companyDto.getMailingAddress());

            session.persist(companyEntity);
            transaction.commit();
        }
    }

    public static void deleteCompany(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Company company = session.find(Company.class, id);
            session.remove(company);
            transaction.commit();
        }
    }

    public static boolean existsByUic(String uic) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery(
                            "SELECT COUNT(c) FROM Company c WHERE c.uic = :uic", long.class)
                    .setParameter("uic", uic)
                    .getSingleResult();
            return count > 0;
        }
    }

    public static boolean existsByUicExcludingId(String uic, long id) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery("SELECT COUNT(c) FROM Company c WHERE c.uic = :uic AND c.id != :id", long.class)
                    .setParameter("uic", uic).setParameter("id", id).getSingleResult();
            return count > 0;
        }
    }

    public static boolean existsByVatNumber(String vatNumber) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery(
                            "SELECT COUNT(c) FROM Company c WHERE c.vatNumber = :vatNumber", long.class)
                    .setParameter("vatNumber", vatNumber)
                    .getSingleResult();
            return count > 0;
        }
    }

    public static boolean existsByVatNumberExcludingId(String vatNumber, long id) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery("SELECT COUNT(c) FROM Company c WHERE c.vatNumber = :vatNumber AND c.id != :id", long.class)
                    .setParameter("vatNumber", vatNumber).setParameter("id", id).getSingleResult();
            return count > 0;
        }
    }

    public static List<CompanyPaymentDto> getCompanyPayments(int year, int month) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<CompanyPaymentDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.CompanyPaymentDto(c.id, c.name, SUM(p.amount), YEAR(p.paymentDate), MONTH(p.paymentDate)) " +
                            " FROM Payment p JOIN FETCH p.employee e JOIN FETCH e.company c" +
                            " WHERE YEAR(p.paymentDate) = :year AND MONTH(p.paymentDate) = :month" +
                            " GROUP BY c.id, c.name, YEAR(p.paymentDate), MONTH(p.paymentDate)",
                    CompanyPaymentDto.class).setParameter("year", year).setParameter("month", month);
            return query.getResultList();
        }
    }

}
