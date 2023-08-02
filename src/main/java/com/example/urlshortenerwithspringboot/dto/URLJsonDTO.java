package com.example.urlshortenerwithspringboot.dto;

import com.example.urlshortenerwithspringboot.model.URL;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
public class URLJsonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2489401343435516702L;

    @JsonProperty("id")
    private Long id;
    @JsonProperty("original_url")
    private String originalURL;
    @JsonProperty("short_url")
    private String shortURL;
    @JsonProperty("creation_date")
    private String creationDate;

    public static URLJsonDTO convert(final URL url) {
        URLJsonDTO urlJsonDTO = new URLJsonDTO();
        urlJsonDTO.setId(url.getId());
        urlJsonDTO.setOriginalURL(url.getOriginalURL());
        urlJsonDTO.setShortURL(url.getShortURL());
        urlJsonDTO.setCreationDate(url.getCreationDate());
        return urlJsonDTO;
    }
}
