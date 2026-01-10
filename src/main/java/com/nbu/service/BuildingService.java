package com.nbu.service;

import com.nbu.dao.BuildingDao;
import com.nbu.dto.BuildingDto;
import com.nbu.dto.BuildingApartmentResidentDto;

import java.util.List;

public class BuildingService {
    public void createBuilding(BuildingDto buildingDto) {
        BuildingDao.createBuilding(buildingDto);
    }

    public BuildingDto getBuilding(long buildingId) {
        return BuildingDao.getBuilding(buildingId);
    }

    public List<BuildingDto> getAllBuildings() {
        return BuildingDao.getBuildings();
    }

    public void updateBuilding(long buildingId, BuildingDto buildingDto) {
        BuildingDao.updateBuilding(buildingId, buildingDto);
    }

    public void deleteBuilding(long buildingId) {
        BuildingDao.deleteBuilding(buildingId);
    }

    public List<BuildingApartmentResidentDto> getBuildingResidents(long buildingId) {
        return BuildingDao.getBuildingResidents(buildingId);
    }


}
