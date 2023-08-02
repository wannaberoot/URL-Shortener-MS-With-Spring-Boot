package com.example.urlshortenerwithspringboot.repository;

import com.example.urlshortenerwithspringboot.model.URL;
import com.example.urlshortenerwithspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {

    Optional<URL> findByIdAndUser(final Long id, final User user);
    Optional<URL> findById(final Long id);
    Optional<List<URL>> findAllByUser(final User user);
}
