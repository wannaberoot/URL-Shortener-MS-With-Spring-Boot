package com.example.urlshortenerwithspringboot.repository;

import com.example.urlshortenerwithspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);
    void deleteByUsername(final String username);
}
