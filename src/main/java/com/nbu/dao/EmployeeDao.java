package com.nbu.dao;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.BuildingDto;
import com.nbu.dto.EmployeeBuildingsDto;
import com.nbu.dto.EmployeeCompanyDto;
import com.nbu.dto.EmployeeDto;
import com.nbu.entity.Building;
import com.nbu.entity.Company;
import com.nbu.entity.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

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
        }
    }

    public static EmployeeDto getEmployee(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Employee employeeEntity = session.createQuery("SELECT e FROM Employee e JOIN FETCH e.company WHERE e.id = :id", Employee.class)
                    .setParameter("id", id)
                    .getSingleResult();
            EmployeeDto employeeDto = new EmployeeDto();
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

    public static void updateEmployee(Long id, EmployeeDto employeeDto) {
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

    public static EmployeeBuildingsDto getEmployeeBuildingsDto(long employeeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Employee employee = session.createQuery(
                            "SELECT e FROM Employee e LEFT JOIN FETCH e.buildings WHERE e.id = :id",
                            Employee.class)
                    .setParameter("id", employeeId)
                    .getSingleResult();

            Set<BuildingDto> buildingDtos = employee.getBuildings().stream()
                    .map(building -> new BuildingDto(
                            building.getId(),
                            building.getAddress(),
                            building.getNumberOfFloors(),
                            building.getNumberOfApartments(),
                            building.getBuiltUpArea(),
                            building.getCommonAreas()
                    ))
                    .collect(java.util.stream.Collectors.toSet());

            return new EmployeeBuildingsDto(
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getMiddleName(),
                    employee.getLastName(),
                    employee.getUcn(),
                    buildingDtos
            );
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

}
