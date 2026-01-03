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
    Long id;
    String firstName;
    String middleName;
    String lastName;
    String ucn;
    LocalDate birthDate;
    Long companyId;
}