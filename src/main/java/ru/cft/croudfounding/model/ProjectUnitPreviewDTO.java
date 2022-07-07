package ru.cft.croudfounding.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectUnitPreviewDTO {
    @JsonProperty("id")
    @Parameter(description = "Уникальный идентификатор")
    @NonNull
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

}
