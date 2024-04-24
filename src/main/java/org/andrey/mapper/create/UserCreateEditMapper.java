package org.andrey.mapper.create;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.User;
import org.andrey.dto.create.UserCreateEditDto;
import org.andrey.mapper.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDto object) {
        return User.builder()
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .email(object.getEmail())
                .birthDate(object.getBirthDate())
                .role(object.getRole())
                .password(Optional.of(object.getRawPassword())
                        .filter(StringUtils::hasText)
                        .map(passwordEncoder::encode)
                        .orElse("123"))
                .build();
    }
}
