package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.request.TaxTypeDto;
import com.nbu.entity.TaxType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TaxTypeDao {
    public static void createTaxType(TaxTypeDto taxTypeDtoDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TaxType taxType = new TaxType();
            taxType.setName(taxTypeDtoDto.getName());
            transaction.commit();
        }
    }

    public static TaxTypeDto getTaxType(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT new com.nbu.dto.request.TaxTypeDto(tt.id, tt.name) FROM TaxType tt WHERE tt.id = :id", TaxTypeDto.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public static List<TaxTypeDto> getTaxTypes() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<TaxTypeDto> criteriaQuery = criteriaBuilder.createQuery(TaxTypeDto.class);
            Root<TaxType> root = criteriaQuery.from(TaxType.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(
                            TaxTypeDto.class,
                            root.get("id"),
                            root.get("name")
                    )
            );

            Query<TaxTypeDto> query = session.createQuery(criteriaQuery);

            return query.getResultList();
        }
    }

    public static void updateTaxType(TaxTypeDto taxTypeDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TaxType taxType = session.find(TaxType.class, taxTypeDto.getId());
            taxType.setName(taxTypeDto.getName());
            session.persist(taxType);
            transaction.commit();
        }
    }

    public static void deleteTaxType(TaxTypeDto taxTypeDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TaxType taxType = session.find(TaxType.class, taxTypeDto.getId());
            session.remove(taxType);
            transaction.commit();
        }
    }

    public static boolean taxTypeExists(long taxTypeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            TaxType taxType = session.find(TaxType.class, taxTypeId);
            return taxType != null;
        }
    }
}
