package com.nbu.entity;

import com.nbu.validator.ValidUCN;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PERSON")
@ToString(callSuper = true)
public class Person extends BaseEntity {

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

    @PastOrPresent(message = "Birth date cannot be in the future!")
    private LocalDate birthDate;

}
