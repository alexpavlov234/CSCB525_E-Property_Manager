package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.PetDto;
import com.nbu.entity.Apartment;
import com.nbu.entity.Pet;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PetDao {

    public static void createPet(PetDto petDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Apartment apartmentEntity = session.find(Apartment.class, petDto.getApartmentId());

            Pet petEntity = new Pet();
            petEntity.setName(petDto.getName());
            petEntity.setApartment(apartmentEntity);

            session.persist(petEntity);
            transaction.commit();

            petDto.setId(petEntity.getId());
        }
    }

    public static PetDto getPet(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT new com.nbu.dto.PetDto(p.id, p.name, a.id) " +
                                    "FROM Pet p JOIN p.apartment a WHERE p.id = :id", PetDto.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public static List<PetDto> getPets() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PetDto> criteriaQuery = criteriaBuilder.createQuery(PetDto.class);
            Root<Pet> root = criteriaQuery.from(Pet.class);

            root.fetch("apartment");

            criteriaQuery.select(
                    criteriaBuilder.construct(PetDto.class,
                            root.get("id"),
                            root.get("name"),
                            root.get("apartment").get("id")
                    )
            );

            Query<PetDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static List<PetDto> getPetsByApartment(long apartmentId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PetDto> criteriaQuery = criteriaBuilder.createQuery(PetDto.class);
            Root<Pet> root = criteriaQuery.from(Pet.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(PetDto.class,
                            root.get("id"),
                            root.get("name"),
                            root.get("apartment").get("id")
                    )
            ).where(criteriaBuilder.equal(root.get("apartment").get("id"), apartmentId));

            Query<PetDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static void updatePet(long id, PetDto petDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pet petEntity = session.find(Pet.class, id);
            Apartment apartmentEntity = session.find(Apartment.class, petDto.getApartmentId());

            petEntity.setName(petDto.getName());
            petEntity.setApartment(apartmentEntity);

            session.persist(petEntity);
            transaction.commit();
        }
    }

    public static void deletePet(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pet pet = session.find(Pet.class, id);
            session.remove(pet);
            transaction.commit();
        }
    }

    public static boolean apartmentExists(long apartmentId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Apartment apartment = session.find(Apartment.class, apartmentId);
            return apartment != null;
        }
    }

    public static long countPetsInApartment(long apartmentId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(p) FROM Pet p WHERE p.apartment.id = :apartmentId", Long.class)
                    .setParameter("apartmentId", apartmentId)
                    .getSingleResult();
            return count != null ? count : 0;
        }
    }
}

