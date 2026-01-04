package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuildingApartmentDto {
    private long buildingId;
    private String buildingAddress;
    private long apartmentId;
    private int apartmentNumber;
    private int floor;
    private double area;
    private long ownerId;
    private String ownerFirstName;
    private String ownerLastName;
}

