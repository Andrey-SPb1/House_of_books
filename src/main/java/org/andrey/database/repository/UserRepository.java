package org.andrey.database.repository;

import org.andrey.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, RevisionRepository<User, Long, Integer> {

    Optional<User> findByEmail(String email);
    @Query(value = "select u.id from users u " +
            "where email = :email", nativeQuery = true)
    Optional<Long> getIdByEmail(String email);

}
