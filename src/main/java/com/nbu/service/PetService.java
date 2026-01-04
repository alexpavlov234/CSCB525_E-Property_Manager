package com.nbu.service;

import com.nbu.dao.PetDao;
import com.nbu.dto.PetDto;

import java.util.List;

public class PetService {

    public void createPet(PetDto petDto) {
        validatePetData(petDto);
        PetDao.createPet(petDto);
    }

    public PetDto getPet(long id) {
        return PetDao.getPet(id);
    }

    public List<PetDto> getAllPets() {
        return PetDao.getPets();
    }

    public List<PetDto> getPetsByApartment(long apartmentId) {
        return PetDao.getPetsByApartment(apartmentId);
    }

    public void updatePet(long id, PetDto petDto) {
        validatePetData(petDto);
        PetDao.updatePet(id, petDto);
    }

    public void deletePet(long id) {
        PetDao.deletePet(id);
    }

    private void validatePetData(PetDto petDto) {
        if (petDto.getName() == null || petDto.getName().isBlank()) {
            throw new IllegalArgumentException("Pet name cannot be empty.");
        }

        if (!PetDao.apartmentExists(petDto.getApartmentId())) {
            throw new IllegalArgumentException("Apartment with id " + petDto.getApartmentId() + " does not exist.");
        }
    }
}

