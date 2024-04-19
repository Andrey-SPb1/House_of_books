package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.repository.UserRepository;
import org.andrey.dto.UserReadDto;
import org.andrey.mapper.UserReadMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

}
