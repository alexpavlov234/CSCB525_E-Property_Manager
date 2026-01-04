package com.nbu.service;

import com.nbu.dao.ApartmentDao;
import com.nbu.dao.PetDao;
import com.nbu.dao.ResidentDao;
import com.nbu.dao.TaxDao;
import com.nbu.dto.TaxDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TaxService {

    // Tax rates - can be configured
    private static final BigDecimal RATE_PER_SQUARE_METER = new BigDecimal("0.50");
    private static final BigDecimal RATE_PER_ELEVATOR_USER = new BigDecimal("5.00");
    private static final BigDecimal RATE_PER_PET = new BigDecimal("3.00");
    private static final int MIN_AGE_FOR_ELEVATOR_TAX = 7;

    public void createTax(TaxDto taxDto) {
        if (!ApartmentDao.apartmentExists(taxDto.getApartmentId())) {
            throw new IllegalArgumentException("Apartment with id " + taxDto.getApartmentId() + " does not exist.");
        }
        calculateTax(taxDto);
        TaxDao.createTax(taxDto);
    }

    public TaxDto getTax(long id) {
        return TaxDao.getTax(id);
    }

    public List<TaxDto> getAllTaxes() {
        return TaxDao.getTaxes();
    }

    public void updateTax(long id, TaxDto taxDto) {
        taxDto.setId(id);
        validateTaxData(taxDto);
        TaxDao.updateTax(taxDto);
    }

    public void deleteTax(long id) {
        TaxDto taxDto = new TaxDto();
        taxDto.setId(id);
        TaxDao.deleteTax(taxDto);
    }

    public TaxDto calculateTax(TaxDto taxDto) {
        if (!ApartmentDao.apartmentExists(taxDto.getApartmentId())) {
            throw new IllegalArgumentException("Apartment with id " + taxDto.getApartmentId() + " does not exist.");
        }

        // Get apartment area
        double area = ApartmentDao.getApartmentArea(taxDto.getApartmentId());

        // Count residents over 7 years old who use the elevator
        long elevatorUsers = ResidentDao.countElevatorUsersOverAge(taxDto.getApartmentId(), MIN_AGE_FOR_ELEVATOR_TAX);

        // Count pets in the apartment
        long petCount = PetDao.countPetsInApartment(taxDto.getApartmentId());

        // Calculate total tax
        BigDecimal areaTax = RATE_PER_SQUARE_METER.multiply(BigDecimal.valueOf(area));
        BigDecimal elevatorTax = RATE_PER_ELEVATOR_USER.multiply(BigDecimal.valueOf(elevatorUsers));
        BigDecimal petTax = RATE_PER_PET.multiply(BigDecimal.valueOf(petCount));

        BigDecimal totalAmount = areaTax.add(elevatorTax).add(petTax).setScale(2, RoundingMode.HALF_UP);

        taxDto.setAmount(totalAmount);

        return taxDto;
    }

    private void validateTaxData(TaxDto taxDto) {
        if (taxDto.getType() == null || taxDto.getType().isBlank()) {
            throw new IllegalArgumentException("Tax type cannot be empty.");
        }

        if (taxDto.getAmount() == null || taxDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tax amount must be greater than zero.");
        }

        if (!ApartmentDao.apartmentExists(taxDto.getApartmentId())) {
            throw new IllegalArgumentException("Apartment with id " + taxDto.getApartmentId() + " does not exist.");
        }
    }
}

