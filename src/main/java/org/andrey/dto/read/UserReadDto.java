package org.andrey.dto.read;

import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@FieldNameConstants
public record UserReadDto(
        String firstname,
        String lastname,
        String email,
        LocalDate birthDate) {

}