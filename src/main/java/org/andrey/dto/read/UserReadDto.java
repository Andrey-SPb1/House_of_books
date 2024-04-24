package org.andrey.dto.read;

import lombok.Value;

import java.time.LocalDate;

@Value
public class UserReadDto {

    Long id;
    String firstname;
    String lastname;
    String email;
    LocalDate birthDate;

}