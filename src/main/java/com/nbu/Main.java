package com.nbu;
import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dao.CompanyDao;
import com.nbu.entity.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {

        Company company = new Company();

        CompanyDao.getCompanies().forEach(System.out::println);
        company.setName("Company 2312");
        company.setUic("Company 123");
        company.setVatNumber("Company VAT 123");
        company.setMailingAddress("Company Mailing 213");
        company.setRegisteredAddress("Company Registered 123");
        CompanyDao.updateCompany(2, company);
        CompanyDao.deleteCompany(4);
        CompanyDao.getCompanies().forEach(System.out::println);
        System.out.println(CompanyDao.getCompany(1));

//        Session session = SessionFactoryUtil.getSessionFactory().openSession();
//        session.close();
    }
}