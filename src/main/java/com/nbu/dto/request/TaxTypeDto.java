package com.nbu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaxTypeDto {
    private long id;
    @NotBlank(message = "The type must not be blank!")
    private String name;
}