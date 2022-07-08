package ru.cft.croudfounding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.croudfounding.model.ErrorDTO;
import ru.cft.croudfounding.model.ProjectUnitDTO;
import ru.cft.croudfounding.service.ProjectService;

import javax.validation.Valid;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Создать проект.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Проект успешно создано.",
                            content = @Content(schema = @Schema(implementation = ProjectUnitDTO.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Невалидная схема документа или входные данные не верны.",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Такой пользователь не найден.",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            })
    @PostMapping("/projects/new")
    public ProjectUnitDTO addProject(@RequestBody @Valid ProjectUnitDTO newProject) {
        return projectService.saveProject(newProject);
    }

}
