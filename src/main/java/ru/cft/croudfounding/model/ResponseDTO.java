package ru.cft.croudfounding.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors(chain = true)
public class ResponseDTO {
    @Schema(description = "Код ответа")
    private final int resultCode;

    @Schema(description = "Сообщение")
    private final String resultMessage;
}
