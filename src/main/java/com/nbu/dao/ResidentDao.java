package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.request.ResidentDto;
import com.nbu.entity.Apartment;
import com.nbu.entity.Resident;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class ResidentDao {
    public static void createResident(ResidentDto residentDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Apartment apartmentEntity = session.find(Apartment.class, residentDto.getApartmentId());

            Resident residentEntity = new Resident();
            residentEntity.setFirstName(residentDto.getFirstName());
            residentEntity.setMiddleName(residentDto.getMiddleName());
            residentEntity.setLastName(residentDto.getLastName());
            residentEntity.setUcn(residentDto.getUcn());
            residentEntity.setBirthDate(residentDto.getBirthDate());
            residentEntity.setUseElevator(residentDto.isUseElevator());
            residentEntity.setApartment(apartmentEntity);
            session.persist(residentEntity);
            transaction.commit();

            residentDto.setId(residentEntity.getId());
        }
    }

    public static ResidentDto getResident(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT new com.nbu.dto.ResidentDto(r.id, r.firstName, r.middleName, r.lastName, r.ucn, r.birthDate, r.useElevator, a.id) " +
                                    "FROM Resident r JOIN FETCH r.apartment a WHERE r.id = :id", ResidentDto.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public static List<ResidentDto> getResidents() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ResidentDto> criteriaQuery = criteriaBuilder.createQuery(ResidentDto.class);
            Root<Resident> root = criteriaQuery.from(Resident.class);

            root.fetch("apartment");

            criteriaQuery.select(
                    criteriaBuilder.construct(ResidentDto.class,
                            root.get("id"),
                            root.get("firstName"),
                            root.get("middleName"),
                            root.get("lastName"),
                            root.get("ucn"),
                            root.get("birthDate"),
                            root.get("useElevator"),
                            root.get("apartment").get("id")
                    )
            );

            Query<ResidentDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static List<ResidentDto> getResidentsByApartment(long apartmentId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ResidentDto> criteriaQuery = criteriaBuilder.createQuery(ResidentDto.class);
            Root<Resident> root = criteriaQuery.from(Resident.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(ResidentDto.class,
                            root.get("id"),
                            root.get("firstName"),
                            root.get("middleName"),
                            root.get("lastName"),
                            root.get("ucn"),
                            root.get("birthDate"),
                            root.get("useElevator"),
                            root.get("apartment").get("id")
                    )
            ).where(criteriaBuilder.equal(root.get("apartment").get("id"), apartmentId));

            Query<ResidentDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static void updateResident(long id, ResidentDto residentDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Resident residentEntity = session.find(Resident.class, id);
            Apartment apartmentEntity = session.find(Apartment.class, residentDto.getApartmentId());

            residentEntity.setFirstName(residentDto.getFirstName());
            residentEntity.setMiddleName(residentDto.getMiddleName());
            residentEntity.setLastName(residentDto.getLastName());
            residentEntity.setUcn(residentDto.getUcn());
            residentEntity.setBirthDate(residentDto.getBirthDate());
            residentEntity.setUseElevator(residentDto.isUseElevator());
            residentEntity.setApartment(apartmentEntity);
            session.persist(residentEntity);
            transaction.commit();
        }
    }

    public static void deleteResident(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Resident resident = session.find(Resident.class, id);
            session.remove(resident);
            transaction.commit();
        }
    }

    public static boolean existsByUCN(String ucn) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery("SELECT COUNT(r) FROM Resident r WHERE r.ucn = :ucn", long.class)
                    .setParameter("ucn", ucn)
                    .getSingleResult();
            return count > 0;
        }
    }

    public static boolean existsByUCNExcludingId(String ucn, long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery("SELECT COUNT(r) FROM Resident r WHERE r.ucn = :ucn AND r.id != :id", long.class)
                    .setParameter("ucn", ucn)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        }
    }

    public static long countElevatorUsersOverAge(long apartmentId, int minAge) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            LocalDate cutoffDate = LocalDate.now().minusYears(minAge);
            Long count = session.createQuery(
                            "SELECT COUNT(r) FROM Resident r WHERE r.apartment.id = :apartmentId " +
                                    "AND r.useElevator = true AND r.birthDate <= :cutoffDate", Long.class)
                    .setParameter("apartmentId", apartmentId)
                    .setParameter("cutoffDate", cutoffDate)
                    .getSingleResult();
            return count != null ? count : 0;
        }
    }
}
