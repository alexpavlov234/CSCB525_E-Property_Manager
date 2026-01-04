package com.nbu.service;

import com.nbu.dao.ApartmentDao;
import com.nbu.dto.ApartmentDto;
import com.nbu.dto.ApartmentResidentDto;

import java.util.List;

public class ApartmentService {

    public void createApartment(ApartmentDto apartmentDto) {
        validateApartmentData(apartmentDto);

        if (ApartmentDao.existsByNumberAndBuilding(apartmentDto.getNumber(), apartmentDto.getBuildingId())) {
            throw new IllegalArgumentException("Apartment with number " + apartmentDto.getNumber() +
                    " already exists in building with id " + apartmentDto.getBuildingId() + ".");
        }

        ApartmentDao.createApartment(apartmentDto);
    }

    public ApartmentDto getApartment(long id) {
        return ApartmentDao.getApartment(id);
    }

    public List<ApartmentDto> getAllApartments() {
        return ApartmentDao.getApartments();
    }

    public void updateApartment(long id, ApartmentDto apartmentDto) {
        apartmentDto.setId(id);
        validateApartmentData(apartmentDto);

        if (ApartmentDao.existsByNumberAndBuildingExcludingId(apartmentDto.getNumber(), apartmentDto.getBuildingId(), id)) {
            throw new IllegalArgumentException("Apartment with number " + apartmentDto.getNumber() +
                    " already exists in building with id " + apartmentDto.getBuildingId() + ".");
        }

        ApartmentDao.updateApartment(apartmentDto);
    }

    public void deleteApartment(long id) {
        ApartmentDao.deleteApartment(id);
    }

    public List<ApartmentResidentDto> getApartmentResidents(long apartmentId) {
        return ApartmentDao.getApartmentResidents(apartmentId);
    }

    private void validateApartmentData(ApartmentDto apartmentDto) {
        if (!ApartmentDao.ownerExists(apartmentDto.getOwnerId())) {
            throw new IllegalArgumentException("Owner with id " + apartmentDto.getOwnerId() + " does not exist.");
        }

        if (!ApartmentDao.buildingExists(apartmentDto.getBuildingId())) {
            throw new IllegalArgumentException("Building with id " + apartmentDto.getBuildingId() + " does not exist.");
        }
    }
}

