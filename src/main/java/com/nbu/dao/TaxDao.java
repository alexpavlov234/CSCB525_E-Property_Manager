package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.EmployeeCompanyDto;
import com.nbu.dto.TaxDto;
import com.nbu.entity.Apartment;
import com.nbu.entity.Tax;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TaxDao {

    public static void createTax(TaxDto taxDto) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Apartment apartment = session.find(Apartment.class, taxDto.getApartmentId());
            Tax tax = new Tax();
            tax.setType(taxDto.getType());
            tax.setAmount(taxDto.getAmount());
            tax.setApartment(apartment);
            session.persist(tax);
            transaction.commit();
        }
    }

    public static TaxDto getTax(long id){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Tax tax = session.find(Tax.class, id);
            TaxDto taxDto = new TaxDto();
            taxDto.setId(tax.getId());
            taxDto.setType(tax.getType());
            taxDto.setAmount(tax.getAmount());
            taxDto.setApartmentId(tax.getApartment().getId());
            return taxDto;
        }
    }

    public static List<TaxDto> getTaxes(){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<TaxDto> criteriaQuery = criteriaBuilder.createQuery(TaxDto.class);
            Root<Tax> root = criteriaQuery.from(Tax.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(
                            TaxDto.class,
                    root.get("id"),
                    root.get("type"),
                    root.get("amount"),
                    root.get("apartment").get("id")
                    )
            );

            Query<TaxDto> query = session.createQuery(criteriaQuery);

            return query.getResultList();
        }
    }

    public static void updateTax(TaxDto taxDto){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Tax tax = session.find(Tax.class, taxDto.getId());
            Apartment apartment = session.find(Apartment.class, taxDto.getApartmentId());
            tax.setType(taxDto.getType());
            tax.setAmount(taxDto.getAmount());
            tax.setApartment(apartment);
            session.persist(tax);
            transaction.commit();
        }
    }

    public static void deleteTax(TaxDto taxDto){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Tax tax = session.find(Tax.class, taxDto.getId());
            session.remove(tax);
            transaction.commit();
        }
    }
}
