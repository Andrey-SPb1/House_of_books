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
                .firstname(object.firstname())
                .lastname(object.lastname())
                .email(object.email())
                .birthDate(object.birthDate())
                .role(object.role())
                .password(Optional.of(object.rawPassword())
                        .filter(StringUtils::hasText)
                        .map(passwordEncoder::encode)
                        .orElseThrow())
                .build();
    }

    @Override
    public User map(UserCreateEditDto userDto, User user) {
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        user.setEmail(userDto.email());
        user.setRole(userDto.role());
        return user;
    }

}
