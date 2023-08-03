package com.example.urlshortenerwithspringboot.service;

import com.example.urlshortenerwithspringboot.dto.CreateShortURLRequest;
import com.example.urlshortenerwithspringboot.dto.ListURLsJsonDTO;
import com.example.urlshortenerwithspringboot.exception.MSException;
import com.example.urlshortenerwithspringboot.model.URL;
import com.example.urlshortenerwithspringboot.model.User;
import com.example.urlshortenerwithspringboot.repository.URLRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.example.urlshortenerwithspringboot.constants.TestConstants.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("Test class for URL operations.")
public class URLServiceTest {

    @Mock
    private URLRepository urlRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private URLService underTest;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        underTest = new URLService(urlRepository, userService);
    }

    @Test
    @DisplayName("Create Short URL test for invalid user.")
    public void createShortURL_withInvalidUser_thenThrowException() {
        // Given
        Mockito.when(userService.getUser(MOCK_USERNAME)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(MSException.class,
                () -> underTest.createShortURL(MOCK_USERNAME, Mockito.any(CreateShortURLRequest.class)));
    }

    @Test
    @DisplayName("Redirect Short URL test for valid URL.")
    public void redirect_withValidURL_thenRedirect() throws MSException {
        // Given
        URL url = new URL();
        url.setId(MOCK_ID);
        url.setOriginalURL(MOCK_ORIGINAL_URL);
        url.setShortURL(MOCK_SHORT_URL);
        Mockito.when(urlRepository.findById(MOCK_ID)).thenReturn(Optional.of(url));

        // When
        String originalURL = underTest.redirect(MOCK_SHORT_URL);

        // Then
        Assertions.assertEquals(MOCK_ORIGINAL_URL, originalURL);
    }

    @Test
    @DisplayName("Redirect Short URL test for invalid URL.")
    public void redirect_withInvalidURL_thenThrowException() {
        // Given
        Mockito.when(urlRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(MSException.class,
                () -> underTest.redirect(MOCK_SHORT_URL));
    }

    @Test
    @DisplayName("List Short URLs test for valid user.")
    public void listShortURLs_withValidUser_thenListShortURLs() throws MSException {
        // Given
        List<URL> urlList = new ArrayList<>();
        URL url1 = new URL();
        URL url2 = new URL();
        User user = new User();
        user.setUsername(MOCK_USERNAME);
        user.setEmail(MOCK_EMAIL);
        url1.setUser(user);
        url2.setUser(user);
        urlList.add(url1);
        urlList.add(url2);
        Mockito.when(urlRepository.findAllByUser(user)).thenReturn(Optional.of(urlList));
        user.setURLs(urlList);
        Mockito.when(userService.getUser(MOCK_USERNAME)).thenReturn(Optional.of(user));

        // When
        ListURLsJsonDTO listURLsJsonDTO = underTest.listShortURLs(MOCK_USERNAME);

        // Then
        Assertions.assertEquals(MOCK_USERNAME, listURLsJsonDTO.getUsername());
        Assertions.assertEquals(2, listURLsJsonDTO.getShortURLList().size());
    }

    @Test
    @DisplayName("List Short URLs test for user has no URL.")
    public void listShortURLs_withUserHasNoURL_thenThrowException() {
        // Given
        User user = new User();
        user.setUsername(MOCK_USERNAME);
        user.setEmail(MOCK_EMAIL);

        // Then
        Assertions.assertThrows(MSException.class,
                () -> underTest.listShortURLs(MOCK_USERNAME));
    }

    @Test
    @DisplayName("List Short URLs test for invalid user.")
    public void listShortURLs_withInvalidUser_thenThrowException() {
        // Given
        Mockito.when(userService.getUser(MOCK_USERNAME)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(MSException.class,
                () -> underTest.listShortURLs(MOCK_USERNAME));
    }

    @Test
    @DisplayName("Terminate Short URL test for invalid URL id.")
    public void terminateShortURL_withInvalidURLId_thenThrowException() {
        // Given
        Mockito.when(userService.getUser(MOCK_USERNAME)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(MSException.class,
                () -> underTest.terminateShortURL(MOCK_USERNAME, String.valueOf(MOCK_ID)));
    }

    @Test
    @DisplayName("Terminate Short URL test for invalid user.")
    public void terminateShortURL_withInvalidUser_thenThrowException() {
        // Given
        User user = new User();
        user.setUsername(MOCK_USERNAME);
        URL url = new URL();
        Mockito.when(userService.getUser(MOCK_USERNAME)).thenReturn(Optional.of(user));
        User user2 = new User();
        user2.setUsername(MOCK_USERNAME2);
        url.setUser(user2);

        // Then
        Assertions.assertThrows(MSException.class,
                () -> underTest.terminateShortURL(MOCK_USERNAME, String.valueOf(MOCK_ID)));
    }
}
