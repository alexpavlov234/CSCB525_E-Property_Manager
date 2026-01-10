package com.nbu.dto;

import com.nbu.validator.ValidUCN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeCompanyDto {
    private long id;

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

    private long companyId;

    @NotBlank(message = "Company name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Company name has to start with capital letter!")
    private String companyName;

    @NotBlank(message = "UIC cannot be blank!")
    @Length(min = 9, max = 9, message = "UIC must be exactly 9 characters long!")
    private String companyUic;
}