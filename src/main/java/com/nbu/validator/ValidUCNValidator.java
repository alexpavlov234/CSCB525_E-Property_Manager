package com.nbu.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;

public class ValidUCNValidator implements ConstraintValidator<ValidUCN, String> {

    private static final int[] CHECKSUM_WEIGHTS = {2, 4, 8, 5, 10, 9, 7, 3, 6};

    @Override
    public void initialize(ValidUCN constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.matches("\\d{10}")) {
            return false;
        }

        if (!isValidDate(value)) {
            return false;
        }

        return isValidChecksum(value);
    }

    private boolean isValidDate(String egn) {
        int year = Integer.parseInt(egn.substring(0, 2));
        int month = Integer.parseInt(egn.substring(2, 4));
        int day = Integer.parseInt(egn.substring(4, 6));

        int actualYear;
        int actualMonth;

        if (month >= 1 && month <= 12) {
            actualYear = 1900 + year;
            actualMonth = month;
        } else if (month >= 21 && month <= 32) {
            actualYear = 1800 + year;
            actualMonth = month - 20;
        } else if (month >= 41 && month <= 52) {
            actualYear = 2000 + year;
            actualMonth = month - 40;
        } else {
            return false;
        }

        try {
            LocalDate.of(actualYear, actualMonth, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    private boolean isValidChecksum(String egn) {
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(egn.charAt(i));
            sum += digit * CHECKSUM_WEIGHTS[i];
        }

        int remainder = sum % 11;
        int expectedChecksum = (remainder < 10) ? remainder : 0;

        int actualChecksum = Character.getNumericValue(egn.charAt(9));
        return expectedChecksum == actualChecksum;
    }
}
