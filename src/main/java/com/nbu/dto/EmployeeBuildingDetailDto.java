package com.nbu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeBuildingDetailDto {
    private long employeeId;

    @NotBlank(message = "First name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "First name has to start with capital letter!")
    private String employeeFirstName;

    @NotBlank(message = "Middle name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Middle name has to start with capital letter!")
    private String employeeMiddleName;

    @NotBlank(message = "Last name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Last name has to start with capital letter!")
    private String employeeLastName;

    private long buildingId;
    @NotBlank(message = "Address cannot be blank!")
    private String buildingAddress;

    @Min(value = 1, message = "Number of floors must be at least 1!")
    private int numberOfFloors;

    @Min(value = 1, message = "Number of apartments must be at least 1!")
    private int numberOfApartments;

}
