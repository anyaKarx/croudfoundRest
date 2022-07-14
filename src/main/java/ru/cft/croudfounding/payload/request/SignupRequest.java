package ru.cft.croudfounding.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
public class SignupRequest {

    @JsonProperty("password")
    @NotNull
    private String password;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("email")
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

}
