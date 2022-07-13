package ru.cft.croudfounding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.cft.croudfounding.model.ErrorDTO;
import ru.cft.croudfounding.model.ProjectUnitPreviewResponseDTO;
import ru.cft.croudfounding.model.UserDTO;
import ru.cft.croudfounding.service.ProjectService;
import ru.cft.croudfounding.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;

    @Operation(summary = "Получить профиль авторизованного пользователя",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Данные о пользователи в json.",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Пользователь не найден.",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            })
    @GetMapping("/{email}")
    public UserDTO get(@PathVariable String email) {
        return userService.getUserDTOByEmail(email);
    }

    @Operation(summary = "Отредактировать пользователя",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Редактирование прошло успешно.",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Невалидная схема документа или входные данные не верны.",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Такой пользователь не найден.",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            })
    @PutMapping("/{email}/edit")
    @Transactional
    public UserDTO update(@PathVariable String email, @RequestBody @Valid UserDTO updatedUser) {
        return userService.prepareAndSave(email, updatedUser);
    }

    @GetMapping("/{email}/projects")
    public ProjectUnitPreviewResponseDTO findAllChildrenProjects(@PathVariable String email) {
        return projectService.findAllByParent(email);
    }
}
