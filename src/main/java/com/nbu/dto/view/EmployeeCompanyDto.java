package com.nbu.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class EmployeeCompanyDto {
    private final long id;

    private final String firstName;


    private final String middleName;

    private final String lastName;

    private final String ucn;

    private final long companyId;

    private final String companyName;

    private final String companyUic;
}