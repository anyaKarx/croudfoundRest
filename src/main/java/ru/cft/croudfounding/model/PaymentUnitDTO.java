package ru.cft.croudfounding.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class PaymentUnitDTO {

    @JsonProperty("id")
    @Parameter(description = "Уникальный идентификатор проекта")
    @NonNull
    private Long id;

    @JsonProperty("parentId")
    @Parameter(description = " Уникальный идентификатор пользователя владельца.")
    @NotNull
    private Long parentId;

    @JsonProperty("donate")
    @Schema(description = "Cумма взноса.")
    @Valid
    private Long payment;

}
