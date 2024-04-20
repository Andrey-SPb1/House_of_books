package org.andrey.mapper;

import org.andrey.database.entity.User;
import org.andrey.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getFirstname(),
                object.getLastname(),
                object.getEmail(),
                object.getBirthDate()
        );
    }
}
