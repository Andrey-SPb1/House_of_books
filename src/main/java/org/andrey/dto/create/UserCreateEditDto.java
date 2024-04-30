package org.andrey.dto.create;

import jakarta.validation.constraints.*;
import org.andrey.database.entity.Role;
import org.andrey.validation.group.Marker;

import java.time.LocalDate;

public record UserCreateEditDto(
        @NotBlank
        String firstname,
        @NotBlank(groups = Marker.UpdateAction.class)
        String lastname,
        @Email
        String email,
        LocalDate birthDate,
        @NotBlank
        @Size(min = 8)
        String rawPassword,
        Role role) {
}
