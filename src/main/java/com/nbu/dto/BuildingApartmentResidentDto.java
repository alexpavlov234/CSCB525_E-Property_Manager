package com.nbu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class BuildingApartmentResidentDto {

    private final long buildingId;

    private final String buildingAddress;

    private final long apartmentId;
    private final int apartmentNumber;
    private final int floor;

    private final long residentId;
    private final String firstName;


    private final String middleName;

    private final String lastName;

    private final LocalDate birthDate;

    private final int age;
    private final boolean useElevator;
}
