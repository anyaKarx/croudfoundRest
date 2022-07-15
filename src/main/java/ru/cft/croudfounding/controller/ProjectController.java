package ru.cft.croudfounding.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.cft.croudfounding.payload.request.ProjectInfoRequest;
import ru.cft.croudfounding.payload.request.UpdateProjectInfoRequest;
import ru.cft.croudfounding.payload.response.ProjectInfoResponse;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.repository.model.User;
import ru.cft.croudfounding.service.DonationService;
import ru.cft.croudfounding.service.ProjectService;
import ru.cft.croudfounding.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final DonationService donationService;
    private final UserService userService;

    @PostMapping("/new")
    public ProjectInfoResponse addProject(@RequestBody @Valid ProjectInfoRequest project) {
        return projectService.createProject(project);
    }

    @GetMapping
    public List<ProjectInfoResponse> getAllProjects(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return projectService.findAll(pageable);
    }

    @GetMapping("/")
    public ProjectInfoResponse getProjectById(@RequestParam(name = "id") Long id) {
        return projectService.getProjectResponseById(id);
    }

    @PostMapping("/donate")
    public ResponseEntity<?> donateToProject(@RequestParam(name = "id") Long id,
                                             @RequestBody Long donateAmount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User donater = userService.findUserByEmail(auth.getName());
        projectService.donateToProject(id, donateAmount);
        Project project = projectService.getProjectById(id);
        donationService.saveDonation(donater, project, donateAmount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit")
    public ResponseEntity<ProjectInfoResponse> editProject(@RequestParam(name = "id") Long id,
                                                           @RequestBody UpdateProjectInfoRequest updateProjectInfoRequest) {
        return ResponseEntity.ok(projectService.updateProjectByName(id, updateProjectInfoRequest));
    }
}
