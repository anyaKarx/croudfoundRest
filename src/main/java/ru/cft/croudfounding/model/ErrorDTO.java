package ru.cft.croudfounding.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    @JsonProperty("code")
    private final Integer code;

    @JsonProperty("message")
    private final String message;
}
