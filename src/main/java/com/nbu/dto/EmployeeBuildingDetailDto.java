package com.nbu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class EmployeeBuildingDetailDto {
    private final long employeeId;

    private final String employeeFirstName;

    private final String employeeMiddleName;

    private final String employeeLastName;

    private final long buildingId;

    private final String buildingAddress;

    private final int numberOfFloors;


    private final int numberOfApartments;

}
