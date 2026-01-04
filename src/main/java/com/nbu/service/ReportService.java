package com.nbu.service;

import com.nbu.dao.ReportDao;
import com.nbu.dto.*;

import java.util.List;

public class ReportService {

    public List<CompanyPaymentDto> getCompaniesByRevenue(int year, int month, boolean ascending) {
        return ReportDao.getCompaniesSortedByRevenue(year, month, ascending);
    }


    public List<EmployeeBuildingsSummaryDto> getEmployeesByName(long companyId, boolean ascending) {
        return ReportDao.getEmployeesSortedByName(companyId, ascending);
    }


    public List<EmployeeBuildingsSummaryDto> getEmployeesByBuildingCount(long companyId, boolean ascending) {
        return ReportDao.getEmployeesSortedByBuildingCount(companyId, ascending);
    }

    public List<BuildingResidentDto> getResidentsByName(long buildingId, boolean ascending) {
        return ReportDao.getResidentsSortedByName(buildingId, ascending);
    }


    public List<BuildingResidentDto> getResidentsByAge(long buildingId, boolean ascending) {
        return ReportDao.getResidentsSortedByAge(buildingId, ascending);
    }


    public List<EmployeeBuildingsSummaryDto> getEmployeeBuildingsSummary(long companyId) {
        return ReportDao.getEmployeeBuildingsSummary(companyId);
    }

    public List<EmployeeBuildingDetailDto> getEmployeeBuildingsDetail(long companyId) {
        return ReportDao.getEmployeeBuildingsDetail(companyId);
    }

    public int getBuildingApartmentsCount(long buildingId) {
        return ReportDao.getBuildingApartmentsCount(buildingId);
    }

    public List<BuildingApartmentDto> getBuildingApartmentsDetail(long buildingId) {
        return ReportDao.getBuildingApartmentsDetail(buildingId);
    }

    public int getBuildingResidentsCount(long buildingId) {
        return ReportDao.getBuildingResidentsCount(buildingId);
    }

    public List<BuildingResidentDto> getBuildingResidentsDetail(long buildingId) {
        return ReportDao.getBuildingResidentsDetail(buildingId);
    }

    public List<TaxSummaryDto> getTaxSummaryByCompany() {
        return ReportDao.getTaxSummaryByCompany();
    }

    public List<TaxSummaryDto> getTaxSummaryByBuilding() {
        return ReportDao.getTaxSummaryByBuilding();
    }

    public List<TaxSummaryDto> getTaxSummaryByEmployee() {
        return ReportDao.getTaxSummaryByEmployee();
    }

    public List<PaymentSummaryDto> getPaymentSummaryByCompany() {
        return ReportDao.getPaymentSummaryByCompany();
    }

    public List<PaymentSummaryDto> getPaymentSummaryByBuilding() {
        return ReportDao.getPaymentSummaryByBuilding();
    }

    public List<PaymentSummaryDto> getPaymentSummaryByEmployee() {
        return ReportDao.getPaymentSummaryByEmployee();
    }
}

