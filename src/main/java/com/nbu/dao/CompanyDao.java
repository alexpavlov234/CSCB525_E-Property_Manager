package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.CompanyDto;
import com.nbu.entity.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            return session.createQuery("SELECT new com.nbu.dto.CompanyDto(c.id, c.name, c.uic, c.vatNumber, c.registeredAddress, c.mailingAddress) FROM Company c", CompanyDto.class).getResultList();
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


}
