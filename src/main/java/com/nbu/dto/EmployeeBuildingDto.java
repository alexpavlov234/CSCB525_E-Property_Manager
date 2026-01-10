package com.nbu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class EmployeeBuildingDto {
    private final long employeeId;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String ucn;

    private final long buildingId;
    private final String buildingAddress;
    private final int numberOfFloors;
}
