package com.nbu.service;

import com.nbu.dao.BuildingDao;
import com.nbu.dto.request.BuildingDto;
import com.nbu.dto.view.BuildingApartmentResidentDto;
import com.nbu.util.ValidatorUtil;

import java.util.List;

public class BuildingService {
    public void createBuilding(BuildingDto buildingDto) {
        validateBuildingData(buildingDto);

        BuildingDao.createBuilding(buildingDto);
    }

    public BuildingDto getBuilding(long buildingId) {
        return BuildingDao.getBuilding(buildingId);
    }

    public List<BuildingDto> getAllBuildings() {
        return BuildingDao.getBuildings();
    }

    public void updateBuilding(long buildingId, BuildingDto buildingDto) {
        validateBuildingData(buildingDto);

        BuildingDao.updateBuilding(buildingId, buildingDto);
    }

    public void deleteBuilding(long buildingId) {
        BuildingDao.deleteBuilding(buildingId);
    }

    public List<BuildingApartmentResidentDto> getBuildingResidents(long buildingId) {
        return BuildingDao.getBuildingResidents(buildingId);
    }

    private void validateBuildingData(BuildingDto buildingDto) {
        ValidatorUtil.validate(buildingDto);

        if (buildingDto.getNumberOfApartments() < buildingDto.getNumberOfFloors()) {
            throw new IllegalArgumentException("Apartments (" +
                    buildingDto.getNumberOfApartments() + ") cannot be fewer than floors (" +
                    buildingDto.getNumberOfFloors() + ").");
        }

        if (buildingDto.getCommonAreas() >= buildingDto.getBuiltUpArea()) {
            throw new IllegalArgumentException("Common areas (" +
                    buildingDto.getCommonAreas() + ") must be less than built-up area (" +
                    buildingDto.getBuiltUpArea() + ").");
        }
    }

}
