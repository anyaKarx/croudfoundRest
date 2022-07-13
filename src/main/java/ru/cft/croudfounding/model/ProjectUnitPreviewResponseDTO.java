package ru.cft.croudfounding.model;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProjectUnitPreviewResponseDTO {
    @Parameter(description = "Экспортируемые элементы")
    private List<ProjectUnitPreviewDTO> items;

}
