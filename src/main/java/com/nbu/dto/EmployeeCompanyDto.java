package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeCompanyDto {
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;
    private long companyId;
    private String companyName;
    private String companyUic;
}