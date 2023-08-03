package com.example.urlshortenerwithspringboot.service;

import com.example.urlshortenerwithspringboot.constants.MSExceptionEnum;
import com.example.urlshortenerwithspringboot.dto.CreateUserRequest;
import com.example.urlshortenerwithspringboot.dto.UserJsonDTO;
import com.example.urlshortenerwithspringboot.exception.MSException;
import com.example.urlshortenerwithspringboot.model.User;
import com.example.urlshortenerwithspringboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserJsonDTO createUser(final CreateUserRequest createUserRequest) throws MSException {
        log.debug("User is being created for username: %s", createUserRequest.getUsername());
        final Optional<User> optionalUser = getUser(createUserRequest.getUsername());
        if (optionalUser.isPresent()) {
            log.debug("User already exists for username: %s", createUserRequest.getUsername());
            throw new MSException(MSExceptionEnum.USER_ALREADY_EXISTS.getErrorCode(), MSExceptionEnum.USER_ALREADY_EXISTS.getErrorMessage(), createUserRequest.getUsername());
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        User user = new User();
        LocalDateTime now = LocalDateTime.now();
        user.setCreationDate(dateTimeFormatter.format(now));
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());
        userRepository.save(user);
        return UserJsonDTO.convert(user);
    }

    @Transactional
    public void deleteUser(final String username) throws MSException {
        log.debug("User is being deleted for username: %s", username);
        final Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.debug("User not found for username: %s", username);
            throw new MSException(MSExceptionEnum.USER_NOT_FOUND.getErrorCode(), MSExceptionEnum.USER_NOT_FOUND.getErrorMessage(), username);
        }
        userRepository.deleteByUsername(optionalUser.get().getUsername());
    }

    public Optional<User> getUser(final String username) {
        return userRepository.findByUsername(username);
    }
}
