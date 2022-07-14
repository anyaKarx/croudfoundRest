package ru.cft.croudfounding.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class LoginRequest {

    @JsonProperty("username")
    @NotBlank
    @NotNull
    private String username;

    @JsonProperty("password")
    @NotNull
    @NotBlank
    private String password;
}
