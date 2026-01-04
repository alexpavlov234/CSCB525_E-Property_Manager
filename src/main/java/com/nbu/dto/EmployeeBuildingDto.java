package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeBuildingDto {
    private long employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;

    private long buildingId;
    private String buildingAddress;
    private int numberOfFloors;
}
