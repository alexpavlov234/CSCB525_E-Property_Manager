package com.nbu.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuildingApartmentDto {
    private long buildingId;

    @NotBlank(message = "Address cannot be blank!")
    private String buildingAddress;

    private long apartmentId;

    @Min(value = 1, message = "Apartment number must be positive!")
    private int apartmentNumber;

    @Min(value = 0, message = "Floor must be zero or positive!")
    private int floor;

    @NotNull(message = "Area must not be null!")
    @Positive(message = "Area must be positive!")
    private double area;

    private long ownerId;

    @NotBlank(message = "First name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "First name has to start with capital letter!")

    private String ownerFirstName;
    @NotBlank(message = "Last name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Last name has to start with capital letter!")
    private String ownerLastName;
}

