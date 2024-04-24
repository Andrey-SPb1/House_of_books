package org.andrey.dto.create;

import lombok.Value;
import org.andrey.database.entity.Role;

import java.time.LocalDate;

@Value
public class UserCreateEditDto {

    // TODO: 24.04.2024 validation
    String firstname;
    String lastname;
    String email;
    LocalDate birthDate;
    String rawPassword;
    Role role;
}
