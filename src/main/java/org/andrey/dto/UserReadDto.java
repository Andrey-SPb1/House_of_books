package org.andrey.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class UserReadDto {

    String firstname;
    String lastname;
    String email;
    LocalDate birthDate;

}