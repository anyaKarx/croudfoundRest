package ru.cft.croudfounding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.cft.croudfounding.model.ErrorDTO;
import ru.cft.croudfounding.model.ProjectUnitDTO;
import ru.cft.croudfounding.model.ProjectUnitPreviewResponseDTO;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.service.ProjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects")
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
    @PostMapping("/new")
    public ProjectUnitDTO addProject(@RequestBody @Valid ProjectUnitDTO newProject) throws IllegalAccessException {
        return projectService.saveProject(newProject);
    }

    @GetMapping
    public List<Project> getAllProjects(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return projectService.findAll(pageable);
    }

    @GetMapping("/{projectName}")
    public Project getProjectByName(@PathVariable String projectName) {
        return projectService.getProjectByName(projectName);
    }

    @PostMapping(" /users/{email}/projects")
    public ProjectUnitPreviewResponseDTO findAllChildrenProjects( @PathVariable String email)
    {
        return projectService.findAllByParent(email);
    }

}
