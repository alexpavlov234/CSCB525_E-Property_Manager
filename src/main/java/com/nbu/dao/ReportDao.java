package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ReportDao {


    public static List<CompanyPaymentDto> getCompaniesSortedByRevenue(int year, int month, boolean ascending) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String orderDir = ascending ? "ASC" : "DESC";
            Query<CompanyPaymentDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.CompanyPaymentDto(c.id, c.name, COALESCE(SUM(p.amount), 0), :year, :month) " +
                            "FROM Company c " +
                            "LEFT JOIN c.employees e " +
                            "LEFT JOIN e.payments p ON YEAR(p.paymentDate) = :year AND MONTH(p.paymentDate) = :month " +
                            "GROUP BY c.id, c.name " +
                            "ORDER BY COALESCE(SUM(p.amount), 0) " + orderDir,
                    CompanyPaymentDto.class)
                    .setParameter("year", year)
                    .setParameter("month", month);
            return query.getResultList();
        }
    }

    public static List<EmployeeBuildingsSummaryDto> getEmployeesSortedByName(long companyId, boolean ascending) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String orderDir = ascending ? "ASC" : "DESC";
            Query<EmployeeBuildingsSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.EmployeeBuildingsSummaryDto(e.id, e.firstName, e.middleName, e.lastName, e.ucn, SIZE(e.buildings)) " +
                            "FROM Employee e " +
                            "WHERE e.company.id = :companyId " +
                            "ORDER BY e.firstName " + orderDir + ", e.middleName " + orderDir + ", e.lastName " + orderDir,
                    EmployeeBuildingsSummaryDto.class)
                    .setParameter("companyId", companyId);
            return query.getResultList();
        }
    }

    public static List<EmployeeBuildingsSummaryDto> getEmployeesSortedByBuildingCount(long companyId, boolean ascending) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String orderDir = ascending ? "ASC" : "DESC";
            Query<EmployeeBuildingsSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.EmployeeBuildingsSummaryDto(e.id, e.firstName, e.middleName, e.lastName, e.ucn, SIZE(e.buildings)) " +
                            "FROM Employee e " +
                            "WHERE e.company.id = :companyId " +
                            "ORDER BY SIZE(e.buildings) " + orderDir,
                    EmployeeBuildingsSummaryDto.class)
                    .setParameter("companyId", companyId);
            return query.getResultList();
        }
    }

    public static List<BuildingApartmentResidentDto> getResidentsSortedByName(long buildingId, boolean ascending) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String orderDir = ascending ? "ASC" : "DESC";
            Query<BuildingApartmentResidentDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.BuildingResidentDto(b.id, b.address, a.id, a.number, a.floor, r.id, r.firstName, r.middleName, r.lastName, r.birthDate, YEAR(CURRENT_DATE) - YEAR(r.birthDate), r.useElevator) " +
                            "FROM Building b JOIN b.apartments a JOIN a.residents r " +
                            "WHERE b.id = :buildingId " +
                            "ORDER BY r.firstName " + orderDir + ", r.middleName " + orderDir + ", r.lastName " + orderDir,
                    BuildingApartmentResidentDto.class)
                    .setParameter("buildingId", buildingId);
            return query.getResultList();
        }
    }

    public static List<BuildingApartmentResidentDto> getResidentsSortedByAge(long buildingId, boolean ascending) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String orderDir = ascending ? "DESC" : "ASC";
            Query<BuildingApartmentResidentDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.BuildingResidentDto(b.id, b.address, a.id, a.number, a.floor, r.id, r.firstName, r.middleName, r.lastName, r.birthDate, YEAR(CURRENT_DATE) - YEAR(r.birthDate), r.useElevator) " +
                            "FROM Building b JOIN b.apartments a JOIN a.residents r " +
                            "WHERE b.id = :buildingId " +
                            "ORDER BY r.birthDate " + orderDir,
                    BuildingApartmentResidentDto.class)
                    .setParameter("buildingId", buildingId);
            return query.getResultList();
        }
    }

    public static List<EmployeeBuildingsSummaryDto> getEmployeeBuildingsSummary(long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<EmployeeBuildingsSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.EmployeeBuildingsSummaryDto(e.id, e.firstName, e.middleName, e.lastName, e.ucn, SIZE(e.buildings)) " +
                            "FROM Employee e " +
                            "WHERE e.company.id = :companyId",
                    EmployeeBuildingsSummaryDto.class)
                    .setParameter("companyId", companyId);
            return query.getResultList();
        }
    }

    public static List<EmployeeBuildingDetailDto> getEmployeeBuildingsDetail(long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<EmployeeBuildingDetailDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.EmployeeBuildingDetailDto(e.id, e.firstName, e.middleName, e.lastName, b.id, b.address, b.numberOfFloors, b.numberOfApartments) " +
                            "FROM Employee e JOIN e.buildings b " +
                            "WHERE e.company.id = :companyId " +
                            "ORDER BY e.lastName, e.firstName, b.address",
                    EmployeeBuildingDetailDto.class)
                    .setParameter("companyId", companyId);
            return query.getResultList();
        }
    }

    public static int getBuildingApartmentsCount(long buildingId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                    "SELECT COUNT(a) FROM Apartment a WHERE a.building.id = :buildingId", Long.class)
                    .setParameter("buildingId", buildingId)
                    .getSingleResult();
            return count.intValue();
        }
    }

    public static List<BuildingApartmentDto> getBuildingApartmentsDetail(long buildingId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<BuildingApartmentDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.BuildingApartmentDto(b.id, b.address, a.id, a.number, a.floor, a.area, a.owner.id, a.owner.firstName, a.owner.lastName) " +
                            "FROM Building b JOIN b.apartments a " +
                            "WHERE b.id = :buildingId " +
                            "ORDER BY a.floor, a.number",
                    BuildingApartmentDto.class)
                    .setParameter("buildingId", buildingId);
            return query.getResultList();
        }
    }

    public static int getBuildingResidentsCount(long buildingId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                    "SELECT COUNT(DISTINCT r) FROM Building b JOIN b.apartments a JOIN a.residents r WHERE b.id = :buildingId", Long.class)
                    .setParameter("buildingId", buildingId)
                    .getSingleResult();
            return count.intValue();
        }
    }

    public static List<BuildingApartmentResidentDto> getBuildingResidentsDetail(long buildingId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<BuildingApartmentResidentDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.BuildingResidentDto(b.id, b.address, a.id, a.number, a.floor, r.id, r.firstName, r.middleName, r.lastName, r.birthDate, YEAR(CURRENT_DATE) - YEAR(r.birthDate), r.useElevator) " +
                            "FROM Building b JOIN b.apartments a JOIN a.residents r " +
                            "WHERE b.id = :buildingId " +
                            "ORDER BY a.floor, a.number, r.lastName, r.firstName",
                    BuildingApartmentResidentDto.class)
                    .setParameter("buildingId", buildingId);
            return query.getResultList();
        }
    }

    public static List<TaxSummaryDto> getTaxSummaryByCompany() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<TaxSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.TaxSummaryDto(c.id, c.name, COALESCE(SUM(t.amount), 0), COUNT(t)) " +
                            "FROM Company c " +
                            "LEFT JOIN c.employees e " +
                            "LEFT JOIN e.buildings b " +
                            "LEFT JOIN b.apartments a " +
                            "LEFT JOIN a.taxes t " +
                            "GROUP BY c.id, c.name " +
                            "ORDER BY c.name",
                    TaxSummaryDto.class);
            return query.getResultList();
        }
    }

    public static List<TaxSummaryDto> getTaxSummaryByBuilding() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<TaxSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.TaxSummaryDto(b.id, b.address, COALESCE(SUM(t.amount), 0), COUNT(t)) " +
                            "FROM Building b " +
                            "LEFT JOIN b.apartments a " +
                            "LEFT JOIN a.taxes t " +
                            "GROUP BY b.id, b.address " +
                            "ORDER BY b.address",
                    TaxSummaryDto.class);
            return query.getResultList();
        }
    }

    public static List<TaxSummaryDto> getTaxSummaryByEmployee() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<TaxSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.TaxSummaryDto(e.id, CONCAT(e.firstName, ' ', e.lastName), COALESCE(SUM(t.amount), 0), COUNT(t)) " +
                            "FROM Employee e " +
                            "LEFT JOIN e.buildings b " +
                            "LEFT JOIN b.apartments a " +
                            "LEFT JOIN a.taxes t " +
                            "GROUP BY e.id, e.firstName, e.lastName " +
                            "ORDER BY e.lastName, e.firstName",
                    TaxSummaryDto.class);
            return query.getResultList();
        }
    }


    public static List<PaymentSummaryDto> getPaymentSummaryByCompany() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<PaymentSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.PaymentSummaryDto(c.id, c.name, COALESCE(SUM(p.amount), 0), COUNT(p)) " +
                            "FROM Company c " +
                            "LEFT JOIN c.employees e " +
                            "LEFT JOIN e.payments p " +
                            "GROUP BY c.id, c.name " +
                            "ORDER BY c.name",
                    PaymentSummaryDto.class);
            return query.getResultList();
        }
    }

    public static List<PaymentSummaryDto> getPaymentSummaryByBuilding() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<PaymentSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.PaymentSummaryDto(b.id, b.address, COALESCE(SUM(p.amount), 0), COUNT(p)) " +
                            "FROM Building b " +
                            "LEFT JOIN b.apartments a " +
                            "LEFT JOIN a.taxes t " +
                            "LEFT JOIN Payment p ON p.tax = t " +
                            "GROUP BY b.id, b.address " +
                            "ORDER BY b.address",
                    PaymentSummaryDto.class);
            return query.getResultList();
        }
    }

    public static List<PaymentSummaryDto> getPaymentSummaryByEmployee() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<PaymentSummaryDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.PaymentSummaryDto(e.id, CONCAT(e.firstName, ' ', e.lastName), COALESCE(SUM(p.amount), 0), COUNT(p)) " +
                            "FROM Employee e " +
                            "LEFT JOIN e.payments p " +
                            "GROUP BY e.id, e.firstName, e.lastName " +
                            "ORDER BY e.lastName, e.firstName",
                    PaymentSummaryDto.class);
            return query.getResultList();
        }
    }
}

