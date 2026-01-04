package com.nbu.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuildingResidentDto {

    private long buildingId;
    private String buildingAddress;

    private long apartmentId;
    private int apartmentNumber;
    private int floor;

    private long residentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private boolean useElevator;
}
