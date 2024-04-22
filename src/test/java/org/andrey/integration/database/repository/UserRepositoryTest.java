package org.andrey.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.andrey.integration.service.IntegrationTestBase;
import org.andrey.database.entity.User;
import org.andrey.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkAudit() {
        User user = userRepository.findById(1L).get();
        user.setBirthDate(user.getBirthDate().plusYears(1));
        userRepository.flush();
        assertEquals("Andrey", user.getModifiedBy());
    }

    @Test
    void checkTest() {
        List<User> users = userRepository.findAll();
        String firstname = users.get(0).getFirstname();
        assertEquals("Ivan", firstname);
    }

}