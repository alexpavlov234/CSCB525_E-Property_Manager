package com.nbu.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuildingDto {
    private long id;

    @NotBlank(message = "Address cannot be blank!")
    private String address;

    @Min(value = 1, message = "Number of floors must be at least 1!")
    private int numberOfFloors;

    @Min(value = 1, message = "Number of apartments must be at least 1!")
    private int numberOfApartments;

    @DecimalMin(value = "0.0", inclusive = false, message = "Built-up area must be greater than 0!")
    private double builtUpArea;

    @DecimalMin(value = "0.0", inclusive = true, message = "Common areas must be at least 0!")
    private double commonAreas;

    private long companyId;
}