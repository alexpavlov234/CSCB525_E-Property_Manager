package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.request.EmployeeDto;
import com.nbu.dto.view.EmployeeBuildingDto;
import com.nbu.dto.view.EmployeeBuildingsSummaryDto;
import com.nbu.dto.view.EmployeeCompanyDto;
import com.nbu.entity.Building;
import com.nbu.entity.Company;
import com.nbu.entity.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public static void createEmployee(EmployeeDto employeeDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Company companyEntity = session.find(Company.class, employeeDto.getCompanyId());

            Employee employeeEntity = new Employee();
            employeeEntity.setFirstName(employeeDto.getFirstName());
            employeeEntity.setMiddleName(employeeDto.getMiddleName());
            employeeEntity.setLastName(employeeDto.getLastName());
            employeeEntity.setUcn(employeeDto.getUcn());
            employeeEntity.setBirthDate(employeeDto.getBirthDate());
            employeeEntity.setCompany(companyEntity);
            session.persist(employeeEntity);
            transaction.commit();

            employeeDto.setId(employeeEntity.getId());
        }
    }

    public static EmployeeDto getEmployee(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Employee employeeEntity = session.createQuery("SELECT e FROM Employee e JOIN FETCH e.company WHERE e.id = :id", Employee.class)
                    .setParameter("id", id)
                    .getSingleResult();
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setId(employeeEntity.getId());
            employeeDto.setFirstName(employeeEntity.getFirstName());
            employeeDto.setMiddleName(employeeEntity.getMiddleName());
            employeeDto.setLastName(employeeEntity.getLastName());
            employeeDto.setUcn(employeeEntity.getUcn());
            employeeDto.setBirthDate(employeeEntity.getBirthDate());
            employeeDto.setCompanyId(employeeEntity.getCompany().getId());
            return employeeDto;
        }
    }

    public static EmployeeCompanyDto getEmployeeCompany(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeCompanyDto> criteriaQuery = criteriaBuilder.createQuery(EmployeeCompanyDto.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(EmployeeCompanyDto.class,
                            root.get("id"),
                            root.get("firstName"),
                            root.get("middleName"),
                            root.get("lastName"),
                            root.get("ucn"),
                            root.get("company").get("id"),
                            root.get("company").get("name"),
                            root.get("company").get("uic")
                    )
            ).where(criteriaBuilder.equal(root.get("id"), id));

            Query<EmployeeCompanyDto> query = session.createQuery(criteriaQuery);
            return query.getSingleResult();
        }
    }

    public static List<EmployeeDto> getEmployees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeDto> criteriaQuery = criteriaBuilder.createQuery(EmployeeDto.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(EmployeeDto.class,
                            root.get("id"),
                            root.get("firstName"),
                            root.get("middleName"),
                            root.get("lastName"),
                            root.get("ucn"),
                            root.get("birthDate"),
                            root.get("company").get("id")
                    )
            );

            Query<EmployeeDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static List<EmployeeCompanyDto> getEmployeeCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeCompanyDto> criteriaQuery = criteriaBuilder.createQuery(EmployeeCompanyDto.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(EmployeeCompanyDto.class,
                            root.get("id"),
                            root.get("firstName"),
                            root.get("middleName"),
                            root.get("lastName"),
                            root.get("ucn"),
                            root.get("company").get("id"),
                            root.get("company").get("name"),
                            root.get("company").get("uic")
                    )
            );

            Query<EmployeeCompanyDto> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static void updateEmployee(long id, EmployeeDto employeeDto) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employeeEntity = session.find(Employee.class, id);
            Company companyEntity = session.find(Company.class, employeeDto.getCompanyId());

            employeeEntity.setFirstName(employeeDto.getFirstName());
            employeeEntity.setMiddleName(employeeDto.getMiddleName());
            employeeEntity.setLastName(employeeDto.getLastName());
            employeeEntity.setUcn(employeeDto.getUcn());
            employeeEntity.setBirthDate(employeeDto.getBirthDate());
            employeeEntity.setCompany(companyEntity);
            session.persist(employeeEntity);
            transaction.commit();
        }
    }

    public static void deleteEmployee(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employee = session.find(Employee.class, id);
            session.remove(employee);
            transaction.commit();
        }
    }

    public static EmployeeBuildingsSummaryDto getEmployeeBuildingsSummaryDto(long employeeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Employee employee = session.createQuery(
                            "SELECT e FROM Employee e LEFT JOIN FETCH e.buildings WHERE e.id = :id",
                            Employee.class)
                    .setParameter("id", employeeId)
                    .getSingleResult();

            int buildingsCount = (employee.getBuildings() != null ? employee.getBuildings().size() : 0);

            return new EmployeeBuildingsSummaryDto(
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getMiddleName(),
                    employee.getLastName(),
                    employee.getUcn(),
                    buildingsCount
            );
        }
    }

    public static List<EmployeeBuildingDto> getEmployeeBuildings(long employeeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<EmployeeBuildingDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.EmployeeBuildingDto(e.id, e.firstName, e.middleName, e.lastName, e.ucn, b.id, b.address, b.numberOfFloors) " +
                            "FROM Employee e JOIN e.buildings b WHERE e.id = :employeeId", EmployeeBuildingDto.class);
            query.setParameter("employeeId", employeeId);
            return query.getResultList();
        }
    }

    public static List<EmployeeBuildingDto> getEmployeeBuildings() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<EmployeeBuildingDto> query = session.createQuery(
                    "SELECT new com.nbu.dto.EmployeeBuildingDto(e.id, e.firstName, e.middleName, e.lastName, e.ucn, b.id, b.address, b.numberOfFloors) " +
                            "FROM Employee e JOIN e.buildings b", EmployeeBuildingDto.class);
            return query.getResultList();
        }
    }

    public static void assignBuildingToEmployee(long employeeId, long buildingId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Employee employee = session.find(Employee.class, employeeId);
            Building building = session.find(Building.class, buildingId);
            building.setManager(employee);
            session.persist(building);
            transaction.commit();
        }
    }

    public static boolean existsByUCN(String ucn) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.ucn = :ucn", long.class)
                    .setParameter("ucn", ucn)
                    .getSingleResult();
            return count > 0;
        }
    }

    public static boolean existsByUCNExcludingId(String ucn, long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            long count = session.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.ucn = :ucn AND e.id != :id", long.class)
                    .setParameter("ucn", ucn)
                    .setParameter("id", id)
                    .getSingleResult();
            return count > 0;
        }
    }

    public static Employee findEmployeeWithLeastBuildings(long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            List<Employee> results = session.createQuery(
                            "SELECT e FROM Employee e " +
                                    "WHERE e.company.id = :companyId " +
                                    "ORDER BY SIZE(e.buildings) ASC", Employee.class)
                    .setParameter("companyId", companyId)
                    .setMaxResults(1)
                    .getResultList();

            if (results.isEmpty()) {
                return null;
            }
            return results.get(0);
        }
    }

    public static void reassignBuildingsFromEmployee(long employeeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Employee employee = session.createQuery(
                            "SELECT e FROM Employee e LEFT JOIN FETCH e.buildings WHERE e.id = :id", Employee.class)
                    .setParameter("id", employeeId)
                    .getSingleResult();

            if (employee.getBuildings() == null || employee.getBuildings().isEmpty()) {
                transaction.commit();
                return;
            }

            long companyId = employee.getCompany().getId();

            List<Employee> otherEmployees = session.createQuery(
                            "SELECT e FROM Employee e " +
                                    "LEFT JOIN FETCH e.buildings " +
                                    "WHERE e.company.id = :companyId AND e.id != :excludeEmployeeId " +
                                    "ORDER BY SIZE(e.buildings) ASC", Employee.class)
                    .setParameter("companyId", companyId)
                    .setParameter("excludeEmployeeId", employeeId)
                    .getResultList();

            if (otherEmployees.isEmpty()) {
                for (Building building : employee.getBuildings()) {
                    building.setManager(null);
                    session.merge(building);
                }
            } else {
                List<Building> buildingsToReassign = new ArrayList<>(employee.getBuildings());
                int employeeIndex = 0;

                for (Building building : buildingsToReassign) {
                    Employee targetEmployee = otherEmployees.get(employeeIndex % otherEmployees.size());
                    building.setManager(targetEmployee);
                    session.merge(building);
                    employeeIndex++;
                }
            }

            transaction.commit();
        }
    }

}
