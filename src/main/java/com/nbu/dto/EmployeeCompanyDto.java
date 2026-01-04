package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeCompanyDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;
    private Long companyId;
    private String companyName;
    private String companyUic;
}