package com.nbu.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PetDto {
    private long id;
    private String name;
    private long apartmentId;
}


