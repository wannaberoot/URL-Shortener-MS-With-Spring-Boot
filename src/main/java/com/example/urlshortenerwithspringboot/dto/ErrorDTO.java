package com.example.urlshortenerwithspringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class ErrorDTO implements Serializable {

    @JsonProperty("error_code")
    private String errorCode;
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonProperty("resource")
    private String resource;
}
