package com.example.urlshortenerwithspringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
public class CreateUserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7669453771809372464L;

    @NotNull
    @NotEmpty
    @JsonProperty("username")
    private String username;
    @NotNull
    @NotEmpty
    @JsonProperty("email")
    private String email;
}
