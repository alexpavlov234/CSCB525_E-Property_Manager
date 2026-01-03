package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.BuildingDto;
import com.nbu.entity.Building;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BuildingDao {
    public static void createBuilding(BuildingDto buildingDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Building building = new Building();
            building.setAddress(buildingDto.getAddress());
            building.setNumberOfFloors(buildingDto.getNumberOfFloors());
            building.setNumberOfApartments(buildingDto.getNumberOfApartments());
            building.setBuiltUpArea(buildingDto.getBuiltUpArea());
            building.setCommonAreas(buildingDto.getCommonAreas());
            session.persist(building);
            transaction.commit();
        }
    }

    public static BuildingDto getBuilding(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Building buildingEntity = session.find(Building.class, id);
            BuildingDto buildingDto = new BuildingDto();
            buildingDto.setAddress(buildingEntity.getAddress());
            buildingDto.setNumberOfFloors(buildingEntity.getNumberOfFloors());
            buildingDto.setNumberOfApartments(buildingEntity.getNumberOfApartments());
            buildingDto.setBuiltUpArea(buildingEntity.getBuiltUpArea());
            buildingDto.setCommonAreas(buildingEntity.getCommonAreas());
            return buildingDto;

        }
    }

    public static List<BuildingDto> getBuildings() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BuildingDto> criteriaQuery = criteriaBuilder.createQuery(BuildingDto.class);
            Root<Building> root = criteriaQuery.from(Building.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(
                            BuildingDto.class,
                            root.get("id"),
                            root.get("address"),
                            root.get("numberOfFloors"),
                            root.get("numberOfApartments"),
                            root.get("builtUpArea"),
                            root.get("commonAreas")
                    )
            );
            Query<BuildingDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static void updateBuilding(long id, BuildingDto buildingDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Building buildingEntity = session.find(Building.class, id);
            buildingEntity.setAddress(buildingDto.getAddress());
            buildingEntity.setNumberOfFloors(buildingDto.getNumberOfFloors());
            buildingEntity.setNumberOfApartments(buildingDto.getNumberOfApartments());
            buildingEntity.setBuiltUpArea(buildingDto.getBuiltUpArea());
            buildingEntity.setCommonAreas(buildingDto.getCommonAreas());

            session.persist(buildingEntity);
            transaction.commit();
        }
    }

    public static void deleteBuilding(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Building building = session.find(Building.class, id);
            session.remove(building);
            transaction.commit();
        }
    }


}
