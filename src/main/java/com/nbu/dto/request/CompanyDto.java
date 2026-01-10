package com.nbu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyDto {
    private long id;

    @NotBlank(message = "Company name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Company name has to start with capital letter!")
    private String name;

    @NotBlank(message = "UIC cannot be blank!")
    @Length(min = 9, max = 9, message = "UIC must be exactly 9 characters long!")
    private String uic;

    @NotBlank(message = "VAT number cannot be blank!")
    @Pattern(regexp = "^(BG\\d{9}|\\d{10})$", message = "VAT number must be 'BG' + 9 digits or 10 digits")
    private String vatNumber;

    @NotBlank(message = "Registered address cannot be blank!")
    private String registeredAddress;

    @NotBlank(message = "Mailing address cannot be blank!")
    private String mailingAddress;
}
