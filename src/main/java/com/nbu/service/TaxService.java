package com.nbu.service;

import com.nbu.dao.*;
import com.nbu.dto.request.TaxDto;
import com.nbu.util.ValidatorUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TaxService {
    private static final BigDecimal RATE_PER_SQUARE_METER = new BigDecimal("0.50");
    private static final BigDecimal RATE_PER_ELEVATOR_USER = new BigDecimal("5.00");
    private static final BigDecimal RATE_PER_PET = new BigDecimal("3.00");
    private static final int MIN_AGE_FOR_ELEVATOR_TAX = 7;

    public void createTax(TaxDto taxDto) {
        if (!ApartmentDao.apartmentExists(taxDto.getApartmentId())) {
            throw new IllegalArgumentException("Apartment with id " + taxDto.getApartmentId() + " does not exist.");
        }
        calculateTax(taxDto);
        validateTaxData(taxDto);
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

        double area = ApartmentDao.getApartmentArea(taxDto.getApartmentId());

        long elevatorUsers = ResidentDao.countElevatorUsersOverAge(taxDto.getApartmentId(), MIN_AGE_FOR_ELEVATOR_TAX);

        long petCount = PetDao.countPetsInApartment(taxDto.getApartmentId());

        BigDecimal areaTax = RATE_PER_SQUARE_METER.multiply(BigDecimal.valueOf(area));
        BigDecimal elevatorTax = RATE_PER_ELEVATOR_USER.multiply(BigDecimal.valueOf(elevatorUsers));
        BigDecimal petTax = RATE_PER_PET.multiply(BigDecimal.valueOf(petCount));

        BigDecimal totalAmount = areaTax.add(elevatorTax).add(petTax).setScale(2, RoundingMode.HALF_UP);

        taxDto.setAmount(totalAmount);

        return taxDto;
    }

    private void validateTaxData(TaxDto taxDto) {

        ValidatorUtil.validate(taxDto);
        if (!TaxTypeDao.taxTypeExists(taxDto.getTaxTypeId())) {
            throw new IllegalArgumentException("Tax type with id " + taxDto.getTaxTypeId() + " does not exist.");
        }

        if (!ApartmentDao.apartmentExists(taxDto.getApartmentId())) {
            throw new IllegalArgumentException("Apartment with id " + taxDto.getApartmentId() + " does not exist.");
        }
    }
}

