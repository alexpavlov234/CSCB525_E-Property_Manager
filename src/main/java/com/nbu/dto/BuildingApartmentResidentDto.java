package com.nbu.dto;

import com.nbu.validator.ValidUCN;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuildingApartmentResidentDto {

    private long buildingId;

    @NotBlank(message = "Address cannot be blank!")
    private String buildingAddress;

    private long apartmentId;
    @Min(value = 1, message = "Apartment number must be positive!")
    private int apartmentNumber;
    @Min(value = 0, message = "Floor must be zero or positive!")
    private int floor;

    private long residentId;
    @NotBlank(message = "First name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "First name has to start with capital letter!")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Middle name has to start with capital letter!")

    private String middleName;

    @NotBlank(message = "Last name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Last name has to start with capital letter!")
    private String lastName;

    @PastOrPresent(message = "Birth date cannot be in the future!")
    private LocalDate birthDate;

    private int age;
    private boolean useElevator;
}
