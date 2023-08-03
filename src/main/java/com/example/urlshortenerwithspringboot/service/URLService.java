package com.example.urlshortenerwithspringboot.service;

import com.example.urlshortenerwithspringboot.constants.MSExceptionEnum;
import com.example.urlshortenerwithspringboot.constants.URLConstants;
import com.example.urlshortenerwithspringboot.dto.CreateShortURLRequest;
import com.example.urlshortenerwithspringboot.dto.ListURLsJsonDTO;
import com.example.urlshortenerwithspringboot.dto.URLJsonDTO;
import com.example.urlshortenerwithspringboot.exception.MSException;
import com.example.urlshortenerwithspringboot.model.URL;
import com.example.urlshortenerwithspringboot.model.User;
import com.example.urlshortenerwithspringboot.repository.URLRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class URLService {

    private final URLRepository urlRepository;
    private final UserService userService;

    @Transactional
    public URLJsonDTO createShortURL(final String username, final CreateShortURLRequest createShortURLRequest) throws MSException {
        log.debug("Short URL is being created.");
        Optional<User> optionalUser = userService.getUser(username);
        if (optionalUser.isEmpty()) {
            throw new MSException(MSExceptionEnum.USER_NOT_FOUND.getErrorCode(), MSExceptionEnum.USER_NOT_FOUND.getErrorMessage(), username);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        final URL url = new URL();
        LocalDateTime now = LocalDateTime.now();
        url.setCreationDate(dateTimeFormatter.format(now));
        url.setUser(optionalUser.get());
        url.setOriginalURL(createShortURLRequest.getLongURL());
        urlRepository.save(url);
        shortenURL(url);
        return URLJsonDTO.convert(url);
    }

    @Transactional
    public String redirect(final String shortURL) throws MSException {
        String base62Part = shortURL.substring(shortURL.length() - 8);
        Long originalURLId = base62ToBase10(base62Part);
        Optional<URL> optionalURL = urlRepository.findById(originalURLId);
        if (optionalURL.isEmpty()) {
            throw new MSException(MSExceptionEnum.URL_NOT_FOUND.getErrorCode(), MSExceptionEnum.URL_NOT_FOUND.getErrorMessage(), shortURL);
        }
        return optionalURL.get().getOriginalURL();
    }

    public ListURLsJsonDTO listShortURLs(final String username) throws MSException {
        Optional<User> optionalUser = userService.getUser(username);
        if (optionalUser.isEmpty()) {
            throw new MSException(MSExceptionEnum.USER_NOT_FOUND.getErrorCode(), MSExceptionEnum.USER_NOT_FOUND.getErrorMessage(), username);
        }
        Optional<List<URL>> optionalURLs = urlRepository.findAllByUser(optionalUser.get());
        if (optionalURLs.isEmpty()) {
            throw new MSException(MSExceptionEnum.USER_HAS_NO_URL.getErrorCode(), MSExceptionEnum.USER_HAS_NO_URL.getErrorMessage(), username);
        }
        List<URLJsonDTO> urlJsonDTOList = new ArrayList<>();
        for (URL url : optionalURLs.get()) {
            urlJsonDTOList.add(URLJsonDTO.convert(url));
        }
        return ListURLsJsonDTO.convert(optionalUser.get(), urlJsonDTOList);
    }

    @Transactional
    public void terminateShortURL(final String username, final String id) throws MSException {
        Optional<URL> optionalURL = getURL(username, id);
        if (optionalURL.isEmpty()) {
            throw new MSException(MSExceptionEnum.URL_NOT_FOUND.getErrorCode(), MSExceptionEnum.URL_NOT_FOUND.getErrorMessage(), id);
        }
        if (optionalURL.get().getUser() != userService.getUser(username).get()) {
            throw new MSException(MSExceptionEnum.NOT_AUTHORIZED.getErrorCode(), MSExceptionEnum.NOT_AUTHORIZED.getErrorMessage(), username);
        }
        urlRepository.deleteById(optionalURL.get().getId());
    }

    private Optional<URL> getURL(final String username, final String id) throws MSException {
        Optional<User> optionalUser = userService.getUser(username);
        if (optionalUser.isEmpty()) {
            throw new MSException(MSExceptionEnum.USER_NOT_FOUND.getErrorCode(), MSExceptionEnum.USER_NOT_FOUND.getErrorMessage(), username);
        }
        return urlRepository.findByIdAndUser(Long.parseLong(id), optionalUser.get());
    }

    private void shortenURL(final URL url) {
        final String shortURL = base10ToBase62(url.getId());
        url.setShortURL(shortURL);
        urlRepository.save(url);
    }

    private int convert(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 36;
        }
        return -1;
    }

    private Long base62ToBase10(String shortURL) {
        Long id = 0L;
        for (int i = 0; i < shortURL.length(); i++) {
            id = id * 62 + convert(shortURL.charAt(i));
        }
        return id;
    }

    private String base10ToBase62(Long id) {
        StringBuilder sb = new StringBuilder();
        while (id != 0) {
            sb.insert(0, URLConstants.URL_CHARS.charAt((int)(id % 62)));
            id /= 62;
        }
        while (sb.length() != 8) {
            sb.insert(0, '0');
        }
        sb.insert(0, URLConstants.ROOT_URL);
        return sb.toString();
    }
}
