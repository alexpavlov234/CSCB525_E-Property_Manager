package com.nbu.service;

import com.nbu.dao.CompanyDao;
import com.nbu.dto.CompanyDto;

import java.util.List;

public class CompanyService {
    public void createCompany(CompanyDto companyDto) {
        if(CompanyDao.existsByUic(companyDto.getUic())) {
            throw new IllegalArgumentException("Company with UIC " + companyDto.getUic() + " already exists.");
        }
        if(CompanyDao.existsByVatNumber(companyDto.getVatNumber())) {
            throw new IllegalArgumentException("Company with Vat Number " + companyDto.getVatNumber() + " already exists.");
        }

        CompanyDao.createCompany(companyDto);
    }

    public void updateCompany(long id, CompanyDto companyDto) {
        if(CompanyDao.existsByUicExcludingId(companyDto.getUic(), id)) {
            throw new IllegalArgumentException("Company with UIC " + companyDto.getUic() + " already exists.");
        }

        if(CompanyDao.existsByVatNumberExcludingId(companyDto.getVatNumber(), id)) {
            throw new IllegalArgumentException("Company with VatNumber " + companyDto.getVatNumber() + " already exists.");
        }

        CompanyDao.updateCompany(id, companyDto);
    }

    public void deleteCompany(long id) {
        CompanyDao.deleteCompany(id);
    }

    public CompanyDto getCompany(long id) {
        return CompanyDao.getCompany(id);
    }

    public List<CompanyDto> getAllCompanies() {
        return CompanyDao.getCompanies();
    }
}
