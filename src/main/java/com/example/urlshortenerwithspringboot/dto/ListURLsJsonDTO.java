package com.example.urlshortenerwithspringboot.dto;

import com.example.urlshortenerwithspringboot.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class ListURLsJsonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4930471893742489783L;

    @JsonProperty("username")
    private String username;
    @JsonProperty("short_url_list")
    private List<URLJsonDTO> shortURLList;

    public static ListURLsJsonDTO convert(final User user, final List<URLJsonDTO> URLJsonDTO) {
        ListURLsJsonDTO listURLsJsonDTO = new ListURLsJsonDTO();
        listURLsJsonDTO.setUsername(user.getUsername());
        listURLsJsonDTO.setShortURLList(URLJsonDTO);
        return listURLsJsonDTO;
    }
}
