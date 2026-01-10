package com.nbu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class BuildingApartmentDto {
    private final long buildingId;

    private final String buildingAddress;

    private final long apartmentId;

    private final int apartmentNumber;

    private final int floor;

    private final double area;

    private final long ownerId;


    private final String ownerFirstName;
    private final String ownerLastName;
}

