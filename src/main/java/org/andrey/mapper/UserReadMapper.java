package org.andrey.mapper;

import org.andrey.database.entity.User;
import org.andrey.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User fromObject) {
        return new UserReadDto(
                fromObject.getFirstname(),
                fromObject.getLastname(),
                fromObject.getEmail(),
                fromObject.getBirthDate()
        );
    }
}
