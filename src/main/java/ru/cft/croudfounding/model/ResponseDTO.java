package ru.cft.croudfounding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO {
    @Schema(description = "Код ответа")
    private final int resultCode;

    @Schema(description = "Сообщение")
    private final String resultMessage;
}
