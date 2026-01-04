package com.nbu.service;

import com.nbu.dao.EmployeeDao;
import com.nbu.dto.EmployeeBuildingDto;
import com.nbu.dto.EmployeeBuildingsSummaryDto;
import com.nbu.dto.EmployeeCompanyDto;
import com.nbu.dto.EmployeeDto;

import java.util.List;

public class EmployeeService {
    public void createEmployee(EmployeeDto employeeDto) {
        if (EmployeeDao.existsByUCN(employeeDto.getUcn())) {
            throw new IllegalArgumentException("Employee with UCN " + employeeDto.getUcn() + " already exists.");
        }

        EmployeeDao.createEmployee(employeeDto);
    }

    public EmployeeDto getEmployee(long id) {
        return EmployeeDao.getEmployee(id);
    }

    public List<EmployeeDto> getAllEmployees() {
        return EmployeeDao.getEmployees();
    }

    public EmployeeBuildingsSummaryDto getEmployeeBuildingsSummaryDto(long employeeId) {
        return EmployeeDao.getEmployeeBuildingsSummaryDto(employeeId);
    }

    public List<EmployeeBuildingDto> getEmployeeBuildings(long employeeId) {
        return EmployeeDao.getEmployeeBuildings(employeeId);
    }


    public List<EmployeeBuildingDto> getEmployeeBuildings() {
        return EmployeeDao.getEmployeeBuildings();
    }

    public List<EmployeeCompanyDto> getEmployeeCompanies() {
       return EmployeeDao.getEmployeeCompanies();
    }

    public EmployeeCompanyDto getEmployeeCompany(long employeeId){
        return EmployeeDao.getEmployeeCompany(employeeId);
    }

    public void updateEmployee(long id, EmployeeDto employeeDto) {
        if (EmployeeDao.existsByUCNExcludingId(employeeDto.getUcn(), id)) {
            throw new IllegalArgumentException("Employee with UCN " + employeeDto.getUcn() + " already exists.");
        }

        EmployeeDao.updateEmployee(id, employeeDto);
    }

    public void deleteEmployee(long id) {
        EmployeeDao.reassignBuildingsFromEmployee(id);
        EmployeeDao.deleteEmployee(id);
    }

    public void assignBuildingToEmployee(long employeeId, long buildingId) {
        EmployeeDao.assignBuildingToEmployee(employeeId, buildingId);
    }


}
