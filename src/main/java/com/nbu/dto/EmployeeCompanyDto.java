package com.nbu.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeCompanyDto {
    Long id;
    String firstName;
    String middleName;
    String lastName;
    String ucn;
    Long companyId;
    String companyName;
    String companyUic;
}