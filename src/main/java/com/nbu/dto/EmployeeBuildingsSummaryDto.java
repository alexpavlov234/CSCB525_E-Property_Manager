package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeBuildingsSummaryDto {
    private long employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;
    private int buildingCount;
}