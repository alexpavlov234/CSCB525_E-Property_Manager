package com.nbu.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResidentDto {
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;
    private LocalDate birthDate;
    private boolean useElevator;
    private long apartmentId;
}