package com.nbu.dto;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeBuildingsDto {
    private Long employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;
    private Set<BuildingDto> buildings;
}