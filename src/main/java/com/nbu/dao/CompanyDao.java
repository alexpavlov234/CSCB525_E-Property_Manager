package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.entity.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompanyDao {

    public static void createCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(company);
            transaction.commit();
        }
    }


    public static List<Company> getCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM Company c", Company.class).getResultList();
        }
    }

    public static Company getCompany(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(Company.class, id);
        }
    }

    public static void updateCompany(long id, Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Company company1 = session.find(Company.class, id);
            company1.setName(company.getName());
            company1.setUic(company.getUic());
            company1.setVatNumber(company.getVatNumber());
            company1.setRegisteredAddress(company.getRegisteredAddress());
            company1.setMailingAddress(company.getMailingAddress());
            session.persist(company1);
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
