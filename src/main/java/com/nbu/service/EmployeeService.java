package com.nbu.service;

import com.nbu.dao.EmployeeDao;
import com.nbu.dto.EmployeeBuildingsDto;
import com.nbu.dto.EmployeeCompanyDto;
import com.nbu.dto.EmployeeDto;

import java.util.List;

public class EmployeeService {
    public void createEmployee(EmployeeDto employeeDto) {
        if (EmployeeDao.existsByUCN(employeeDto.getUcn())) {
            throw new IllegalArgumentException("Employee with UCN " + employeeDto.getUcn() + " already exists.");
        } else {
            EmployeeDao.createEmployee(employeeDto);
        }

        EmployeeDao.createEmployee(employeeDto);
    }

    public EmployeeDto getEmployee(long id) {
        return EmployeeDao.getEmployee(id);
    }

    public List<EmployeeDto> getAllEmployees() {
        return EmployeeDao.getEmployees();
    }

    public EmployeeBuildingsDto getEmployeeBuildingsDto(long employeeId) {
        return EmployeeDao.getEmployeeBuildingsDto(employeeId);
    }

    public List<EmployeeCompanyDto> getEmployeeCompanies() {
       return EmployeeDao.getEmployeeCompanies();
    }

    public EmployeeCompanyDto getEmployeeCompany(long employeeId){
        return EmployeeDao.getEmployeeCompany(employeeId);
    }

    public void updateEmployee(Long id, EmployeeDto employeeDto) {
        if (EmployeeDao.existsByUCNExcludingId(employeeDto.getUcn(), id)) {
            throw new IllegalArgumentException("Employee with UCN " + employeeDto.getUcn() + " already exists.");
        }

        EmployeeDao.updateEmployee(id, employeeDto);
    }

    public void deleteEmployee(long id) {
        EmployeeDao.deleteEmployee(id);
    }

    public void assignBuildingToEmployee(long employeeId, long buildingId) {
        EmployeeDao.assignBuildingToEmployee(employeeId, buildingId);
    }


}
