package ru.cft.croudfounding.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  {

    @JsonProperty("id")
    @Schema(example = "456", required = true, description = "уникальный идентификатор," +
            " ставится на стороне бэка")
    private long id;

    @JsonProperty("name")
    @Schema(example = "Петров Петр Петрович", required = true, description = "Фио пользователя")
    @NotNull
    private String name;

    @JsonProperty("password")
    @Schema(example = "sdfghj678", required = true, description = "Пароль")
    @NotNull
    private String password;

}
