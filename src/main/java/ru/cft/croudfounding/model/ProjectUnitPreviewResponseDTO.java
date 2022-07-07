package ru.cft.croudfounding.model;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectUnitPreviewResponseDTO {
    @Parameter(description = "Экспортируемые элементы")
    private List<ProjectUnitPreviewDTO> items;

}
