package com.example.urlshortenerwithspringboot.service;

import com.example.urlshortenerwithspringboot.dto.CreateUserRequest;
import com.example.urlshortenerwithspringboot.dto.UserJsonDTO;
import com.example.urlshortenerwithspringboot.exception.MSException;
import com.example.urlshortenerwithspringboot.model.User;
import com.example.urlshortenerwithspringboot.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import static com.example.urlshortenerwithspringboot.constants.TestConstants.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("Test class for User operations.")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService underTest;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository);
    }

    @Test
    @DisplayName("Create User test for valid username.")
    public void createUser_withValidUsername_thenCreateAndReturnUser() throws MSException {
        // Given
        Mockito.when(userRepository.findByUsername(MOCK_USERNAME)).thenReturn(Optional.empty());
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(MOCK_USERNAME);
        createUserRequest.setEmail(MOCK_EMAIL);

        // When
        UserJsonDTO userJsonDTO = underTest.createUser(createUserRequest);

        // Then
        Assertions.assertEquals(MOCK_USERNAME, userJsonDTO.getUsername());
        Assertions.assertEquals(MOCK_EMAIL, userJsonDTO.getEmail());
    }

    @Test
    @DisplayName("Create User test for already exists username.")
    public void createUser_withAlreadyExistsUsername_thenThrowException() {
        // Given
        User user = new User();
        user.setUsername(MOCK_USERNAME);
        Mockito.when(userRepository.findByUsername(MOCK_USERNAME)).thenReturn(Optional.of(user));
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(MOCK_USERNAME);

        // Then
        Assertions.assertThrows(MSException.class,
                () -> underTest.createUser(createUserRequest));
    }

    @Test
    @DisplayName("Delete User test for invalid username.")
    public void terminateShortURL_withInvalidURLId_thenThrowException() {
        // Given
        Mockito.when(userRepository.findByUsername(MOCK_USERNAME)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(MSException.class,
                () -> underTest.deleteUser(MOCK_USERNAME));
    }
}
