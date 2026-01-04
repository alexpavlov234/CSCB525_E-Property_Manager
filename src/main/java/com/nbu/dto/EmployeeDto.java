package com.nbu.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;
    private LocalDate birthDate;
    private Long companyId;
}