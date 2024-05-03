package org.andrey.dto.create;

import jakarta.validation.constraints.*;
import lombok.experimental.FieldNameConstants;
import org.andrey.database.entity.Role;
import org.andrey.validation.group.Marker;

import java.time.LocalDate;

@FieldNameConstants
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
