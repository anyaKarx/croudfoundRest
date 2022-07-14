package ru.cft.croudfounding.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;
}
