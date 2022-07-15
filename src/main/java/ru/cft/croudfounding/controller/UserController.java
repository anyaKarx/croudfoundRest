package ru.cft.croudfounding.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.croudfounding.payload.request.SignupRequest;
import ru.cft.croudfounding.payload.response.ProjectsUnitPreviewResponse;
import ru.cft.croudfounding.payload.response.UserInfoResponse;
import ru.cft.croudfounding.service.ProjectService;
import ru.cft.croudfounding.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/{userId}")
    public UserInfoResponse get(@PathVariable Long userId) {
        return userService.findUserDTOById(userId);
    }

    @PutMapping("/profile/edit")
    public UserInfoResponse update(@RequestBody @Valid SignupRequest updatedUser) {
        return userService.updateUserInfo(updatedUser);
    }

    @GetMapping(value = "/{userId}/projects")
    public ProjectsUnitPreviewResponse findAllChildrenProjects(@PathVariable Long userId) {
        return projectService.findAllByParent(userId);
    }
}
