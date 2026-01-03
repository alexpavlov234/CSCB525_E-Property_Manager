package com.nbu.dto;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeBuildingsDto {
    Long employeeId;
    String firstName;
    String middleName;
    String lastName;
    String ucn;
    Set<BuildingDto> buildings;
}