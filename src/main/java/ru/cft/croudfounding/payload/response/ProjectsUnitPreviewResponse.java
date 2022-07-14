package ru.cft.croudfounding.payload.response;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProjectsUnitPreviewResponse {
    @Parameter(description = "Экспортируемые элементы")
    private List<ProjectUnitPreviewResponse> items;

}
