package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApartmentResidentDto {
    private long id;
    private int number;
    private int floor;

    private long residentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ucn;
    private boolean useElevator;
}