package com.example.urlshortenerwithspringboot.controller;

import com.example.urlshortenerwithspringboot.constants.URLConstants;
import com.example.urlshortenerwithspringboot.dto.CreateShortURLRequest;
import com.example.urlshortenerwithspringboot.dto.ErrorDTO;
import com.example.urlshortenerwithspringboot.dto.ListURLsJsonDTO;
import com.example.urlshortenerwithspringboot.dto.URLJsonDTO;
import com.example.urlshortenerwithspringboot.exception.MSException;
import com.example.urlshortenerwithspringboot.service.URLService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import javax.validation.Valid;

@Tag(name = "URL", description = "APIs for URL operations.")
@Validated
@RestController
@RequestMapping(URLConstants.BASE_REQUEST_PATH)
@Slf4j
@RequiredArgsConstructor
public class URLController {

    private final URLService urlService;

    @Operation(summary = "Create URL", description = "Create a short URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created.",
                    content = @Content(schema = @Schema(implementation = URLJsonDTO.class))),
            @ApiResponse(responseCode = "500", description = "Something went wrong.",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @PostMapping(URLConstants.USER_PATH + URLConstants.USERNAME_PATH + URLConstants.URL_PATH)
    public ResponseEntity<URLJsonDTO> createShortURL(@PathVariable final String username, @Valid @RequestBody final CreateShortURLRequest createShortURLRequest) throws MSException {
        log.info(String.format("Create short URL request received with long URL: %s", createShortURLRequest.getLongURL()));
        final URLJsonDTO urlJsonDTO = urlService.createShortURL(username, createShortURLRequest);
        log.info(String.format("Short URL has been created with id: %s", urlJsonDTO.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(urlJsonDTO);
    }

    @Operation(summary = "Redirect URL", description = "Redirect to original URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Successfully redirected."),
            @ApiResponse(responseCode = "500", description = "Something went wrong.",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @GetMapping(URLConstants.SHORT_URL_PATH)
    public RedirectView redirect(@PathVariable final String shortURL) throws MSException {
        log.info(String.format("Redirect short URL request received with short URL: %s", shortURL));
        final String originalURL = urlService.redirect(shortURL);
        log.info(String.format("Short URL has been redirected with originalURL URL: %s", originalURL));
        RedirectView redirectView = new RedirectView();
        redirectView.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
        redirectView.setUrl(originalURL);
        return redirectView;
    }

    @Operation(summary = "List URLs", description = "List of user's URLs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully listed.",
                    content = @Content(schema = @Schema(implementation = ListURLsJsonDTO.class))),
            @ApiResponse(responseCode = "500", description = "Something went wrong.",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @GetMapping(URLConstants.USER_PATH + URLConstants.USERNAME_PATH + URLConstants.URL_PATH)
    public ResponseEntity<ListURLsJsonDTO> listShortURLs(@PathVariable final String username) throws MSException {
        log.info(String.format("List short URL request received with username: %s", username));
        final ListURLsJsonDTO listURLsJsonDTO = urlService.listShortURLs(username);
        log.info(String.format("Short URLs have been listed with username: %s", username));
        return ResponseEntity.status(HttpStatus.OK).body(listURLsJsonDTO);
    }

    @Operation(summary = "Terminate URL", description = "Terminate a short URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully terminated."),
            @ApiResponse(responseCode = "500", description = "Something went wrong.",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @DeleteMapping(URLConstants.USER_PATH + URLConstants.USERNAME_PATH + URLConstants.URL_PATH + URLConstants.ID_PATH)
    public ResponseEntity<?> terminateShortURL(@PathVariable final String username, @PathVariable final String id) throws MSException {
        log.info(String.format("Delete short URL request received with id: %s", id));
        urlService.terminateShortURL(username, id);
        log.info(String.format("Short URL has been deleted with id: %s", id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
