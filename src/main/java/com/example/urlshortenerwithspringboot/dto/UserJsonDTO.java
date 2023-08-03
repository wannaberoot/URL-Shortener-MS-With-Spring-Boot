package com.example.urlshortenerwithspringboot.dto;

import com.example.urlshortenerwithspringboot.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
public class UserJsonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2736215277989176542L;

    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("creation_date")
    private String creationDate;

    public static UserJsonDTO convert(final User user) {
        UserJsonDTO userJsonDTO = new UserJsonDTO();
        userJsonDTO.setId(user.getId());
        userJsonDTO.setUsername(user.getUsername());
        userJsonDTO.setEmail(user.getEmail());
        userJsonDTO.setCreationDate(user.getCreationDate());
        return userJsonDTO;
    }
}
