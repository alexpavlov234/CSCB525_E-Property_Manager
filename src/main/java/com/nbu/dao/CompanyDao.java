package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.CompanyDto;
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
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CompanyDto> cr = cb.createQuery(CompanyDto.class);
            Root<Company> root = cr.from(Company.class);

            cr.select(
                    cb.construct(
                            CompanyDto.class,
                            root.get("id"),
                            root.get("name"),
                            root.get("uic"),
                            root.get("vatNumber"),
                            root.get("registeredAddress"),
                            root.get("mailingAddress")
                    ));

            Query<CompanyDto> query = session.createQuery(cr);
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
            Long count = session.createQuery(
                            "SELECT COUNT(c) FROM Company c WHERE c.uic = :uic", Long.class)
                    .setParameter("uic", uic)
                    .getSingleResult();
            return count > 0;
        }
    }

    public static boolean existsByUicExcludingId(String uic, long id) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("SELECT COUNT(c) FROM Company c WHERE c.uic = :uic AND c.id != :id", Long.class)
                    .setParameter("uic", uic).setParameter("id", id).getSingleResult();
            return count > 0;
        }
    }

    public static boolean existsByVatNumber(String vatNumber) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(c) FROM Company c WHERE c.vatNumber = :vatNumber", Long.class)
                    .setParameter("vatNumber", vatNumber)
                    .getSingleResult();
            return count > 0;
        }
    }

    public static boolean existsByVatNumberExcludingId(String vatNumber, long id) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("SELECT COUNT(c) FROM Company c WHERE c.vatNumber = :vatNumber AND c.id != :id", Long.class)
                    .setParameter("vatNumber", vatNumber).setParameter("id", id).getSingleResult();
            return count > 0;
        }
    }

}
