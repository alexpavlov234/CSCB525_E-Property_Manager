package com.nbu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PetDto {
    private long id;

    @NotBlank(message = "Pet name must not be blank!")
    private String name;
    private long apartmentId;
}


