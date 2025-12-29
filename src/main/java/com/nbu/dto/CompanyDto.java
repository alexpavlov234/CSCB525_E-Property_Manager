package com.nbu.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyDto {
    private long id;

    private String name;

    private String uic;

    private String vatNumber;

    private String registeredAddress;

    private String mailingAddress;
}
