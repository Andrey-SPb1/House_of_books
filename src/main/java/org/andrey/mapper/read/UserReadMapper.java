package org.andrey.mapper.read;

import org.andrey.database.entity.User;
import org.andrey.dto.read.UserReadDto;
import org.andrey.mapper.Mapper;
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
