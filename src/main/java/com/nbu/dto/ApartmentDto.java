package com.nbu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApartmentDto {
    private long id;

    @Min(value = 1, message = "Apartment number must be positive!")
    private int number;

    @Min(value = 0, message = "Floor must be zero or positive!")
    private int floor;

    @Positive(message = "Area must be positive!")
    private double area;

    private long ownerId;
    private long buildingId;
}