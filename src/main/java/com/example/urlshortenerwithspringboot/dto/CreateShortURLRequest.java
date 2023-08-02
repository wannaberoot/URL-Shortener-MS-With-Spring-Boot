package com.example.urlshortenerwithspringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
public class CreateShortURLRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4398236644682276141L;

    @NotNull
    @NotEmpty
    @JsonProperty("long_url")
    private String longURL;
}
