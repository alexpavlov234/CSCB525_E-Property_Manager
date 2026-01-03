package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuildingDto {
    private long id;

    private String address;

    private int numberOfFloors;

    private int numberOfApartments;

    private double builtUpArea;

    private double commonAreas;
}