package ru.cft.croudfounding.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {

    @JsonProperty("error_code")
    private final Integer errorCode;

    @JsonProperty("message")
    private final String message;
}
