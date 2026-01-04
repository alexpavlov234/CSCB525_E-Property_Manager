package com.nbu.service;

import com.nbu.dao.ResidentDao;
import com.nbu.dto.ResidentDto;

import java.util.List;

public class ResidentService {
    public void createResident(ResidentDto residentDto) {
        if (ResidentDao.existsByUCN(residentDto.getUcn())) {
            throw new IllegalArgumentException("Resident with UCN " + residentDto.getUcn() + " already exists.");
        }

        ResidentDao.createResident(residentDto);
    }

    public ResidentDto getResident(long id) {
        return ResidentDao.getResident(id);
    }

    public List<ResidentDto> getAllResidents() {
        return ResidentDao.getResidents();
    }

    public List<ResidentDto> getResidentsByApartment(long apartmentId) {
        return ResidentDao.getResidentsByApartment(apartmentId);
    }

    public void updateResident(long id, ResidentDto residentDto) {
        if (ResidentDao.existsByUCNExcludingId(residentDto.getUcn(), id)) {
            throw new IllegalArgumentException("Resident with UCN " + residentDto.getUcn() + " already exists.");
        }

        ResidentDao.updateResident(id, residentDto);
    }

    public void deleteResident(long id) {
        ResidentDao.deleteResident(id);
    }
}
