package ru.cft.croudfounding.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProjectUnitPreviewResponse {
    @JsonProperty("id")
    @Parameter(description = "Уникальный идентификатор")
    @NotNull
    private Long id;

    @JsonProperty("name")
    @Parameter(description = "Название проекта.")
    @NotNull
    private String name;

    @JsonProperty("parentId")
    @Parameter(description = " Уникальный идентификатор пользователя владельца.")
    @NotNull
    private Long parentId;

    @JsonProperty("parentName")
    @Parameter(description = " Фио идентификатор пользователя владельца.")
    @NotNull
    private String parentName;

    @JsonProperty("required amount")
    @Parameter(description = "Целое число, сумма сбора.")
    @NotNull
    private Long requiredAmount;

    @JsonProperty("collected amount")
    @Parameter(description = "Целое число, уже собранная сумма.")
    @NotNull
    private Long collectedAmount;

    @Parameter(description = "Дата открытия сбора.")
    private LocalDateTime startDate;

    @JsonProperty("End date")
    @Parameter(description = " Дата окончания сбора.")
    @NotNull
    @Valid
    private LocalDateTime endDate;

}
