package ru.cft.croudfounding.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserInfoResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String username;

    public UserInfoResponse(Long id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
