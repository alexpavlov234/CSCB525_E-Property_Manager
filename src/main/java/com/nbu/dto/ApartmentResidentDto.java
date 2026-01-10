package com.nbu.dto;

import com.nbu.validator.ValidUCN;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApartmentResidentDto {
    private long id;

    @Min(value = 1, message = "Apartment number must be positive!")
    private int number;
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

    @NotBlank(message = "UCN cannot be blank!")
    @ValidUCN(message = "UCN is not valid!")
    private String ucn;
    private boolean useElevator;
}