package com.nbu.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApartmentDto {
    private long id;
    private int number;
    private int floor;
    private double area;
    private long ownerId;
    private long buildingId;
}