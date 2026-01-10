package com.nbu.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class EmployeeBuildingsSummaryDto {
    private final long employeeId;

    private final String firstName;

    private final String middleName;

    private final String lastName;

    private final String ucn;

    private final int buildingCount;
}