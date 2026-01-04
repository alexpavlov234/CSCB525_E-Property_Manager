package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.ApartmentDto;
import com.nbu.dto.ApartmentResidentDto;
import com.nbu.entity.Apartment;
import com.nbu.entity.Building;
import com.nbu.entity.Resident;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ApartmentDao {
    public static void createApartment(ApartmentDto apartmentDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(apartmentDto);
            transaction.commit();
        }
    }

    public static ApartmentDto getApartment(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(ApartmentDto.class, id);
        }
    }


    public static List<ApartmentDto> getApartments() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApartmentDto> criteriaQuery = criteriaBuilder.createQuery(ApartmentDto.class);

            Root<Apartment> root = criteriaQuery.from(Apartment.class);
            root.fetch("owner");
            root.fetch("building");
            criteriaQuery.select(criteriaBuilder.construct(
                    ApartmentDto.class,
                    root.get("id"),
                    root.get("number"),
                    root.get("floor"),
                    root.get("area"),
                    root.get("price"),
                    root.get("owner").get("id"),
                    root.get("building").get("id")
            ));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public static void updateApartment(ApartmentDto apartmentDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Apartment apartmentEntity = session.find(Apartment.class, apartmentDto.getId());
            Resident residentEntity = session.find(Resident.class, apartmentDto.getOwnerId());
            Building buildingEntity = session.find(Building.class, apartmentDto.getBuildingId());

            apartmentEntity.setId(apartmentDto.getId());
            apartmentEntity.setNumber(apartmentDto.getNumber());
            apartmentEntity.setFloor(apartmentDto.getFloor());
            apartmentEntity.setArea(apartmentDto.getArea());
            apartmentEntity.setOwner(residentEntity);
            apartmentEntity.setBuilding(buildingEntity);
            session.persist(apartmentEntity);

            transaction.commit();
        }

    }

    public static void deleteApartment(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Apartment apartment = session.find(Apartment.class, id);
            session.remove(apartment);
            transaction.commit();
        }
    }

    public static List<ApartmentResidentDto> getApartmentResidents(long apartmentId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT new com.nbu.dto.ApartmentResidentDto(a.id, a.number, a.floor, r.id, r.firstName, r.middleName, r.lastName, r.ucn, r.useElevator) FROM Apartment a "
                            + "JOIN FETCH a.residents r "
                            + "JOIN FETCH a.owner o "
                            + "WHERE a.id = :apartmentId", ApartmentResidentDto.class)
                    .setParameter("apartmentId", apartmentId)
                    .getResultList();

        }
    }
}
