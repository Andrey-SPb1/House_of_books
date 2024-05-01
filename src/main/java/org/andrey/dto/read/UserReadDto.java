package org.andrey.dto.read;

import java.time.LocalDate;

public record UserReadDto(
        String firstname,
        String lastname,
        String email,
        LocalDate birthDate) {

}