package com.example.urlshortenerwithspringboot.controller;

import com.example.urlshortenerwithspringboot.constants.URLConstants;
import com.example.urlshortenerwithspringboot.dto.CreateUserRequest;
import com.example.urlshortenerwithspringboot.dto.ErrorDTO;
import com.example.urlshortenerwithspringboot.dto.UserJsonDTO;
import com.example.urlshortenerwithspringboot.exception.MSException;
import com.example.urlshortenerwithspringboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "APIs for User operations.")
@Validated
@RestController
@RequestMapping(URLConstants.BASE_REQUEST_PATH)
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create User", description = "Create a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created.",
                    content = @Content(schema = @Schema(implementation = UserJsonDTO.class))),
            @ApiResponse(responseCode = "500", description = "Something went wrong.",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @PostMapping(URLConstants.USER_PATH)
    public ResponseEntity<UserJsonDTO> createUser(@Valid @RequestBody final CreateUserRequest createUserRequest) throws MSException {
        log.debug(String.format("Create user request received with username: %s", createUserRequest.getUsername()));
        UserJsonDTO userJsonDTO = userService.createUser(createUserRequest);
        log.debug(String.format("User has been created with id: %s", userJsonDTO.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userJsonDTO);

    }

    @Operation(summary = "Delete User", description = "Delete a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted."),
            @ApiResponse(responseCode = "500", description = "Something went wrong.",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    @DeleteMapping(URLConstants.USER_PATH + URLConstants.USERNAME_PATH)
    public ResponseEntity<?> deleteUser(@PathVariable final String username) throws MSException {
        log.debug(String.format("Delete user request received with username: %s", username));
        userService.deleteUser(username);
        log.debug(String.format("User has been deleted for username: %s", username));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
