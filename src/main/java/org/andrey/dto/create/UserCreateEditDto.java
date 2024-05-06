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
        String lastname,
        @Email
        String email,
        LocalDate birthDate,
        @NotBlank(groups = Marker.CreateAction.class)
        @Size(min = 8, groups = Marker.CreateAction.class)
        @Null(groups = Marker.UpdateAction.class)
        String rawPassword,
        Role role) {
}
