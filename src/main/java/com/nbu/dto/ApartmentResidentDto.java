package com.nbu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ApartmentResidentDto {
    private final long id;

    private final int number;

    private final int floor;

    private final long residentId;
    private final String firstName;


    private final String middleName;

    private final String lastName;

    private final String ucn;
    private final boolean useElevator;
}