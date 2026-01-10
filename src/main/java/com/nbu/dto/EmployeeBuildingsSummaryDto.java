package com.nbu.dto;

import com.nbu.validator.ValidUCN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeBuildingsSummaryDto {
    private long employeeId;

    @NotBlank(message = "First name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "First name has to start with capital letter!")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Middle name has to start with capital letter!")

    private String middleName;

    @NotBlank(message = "Last name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Last name has to start with capital letter!")
    private String lastName;

    @NotBlank(message = "UCN cannot be blank!")
    @ValidUCN(message = "UCN is not valid!")
    private String ucn;

    @Positive(message = "Building count must be positive!")
    private int buildingCount;
}