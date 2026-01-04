package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeBuildingDetailDto {
    private long employeeId;
    private String employeeFirstName;
    private String employeeMiddleName;
    private String employeeLastName;
    private long buildingId;
    private String buildingAddress;
    private int numberOfFloors;
    private int numberOfApartments;
}
