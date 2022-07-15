package ru.cft.croudfounding.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.cft.croudfounding.payload.request.DonateRequest;
import ru.cft.croudfounding.payload.request.ProjectInfoRequest;
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

    @GetMapping("/{projectName}")
    public ProjectInfoResponse getProjectByName(@PathVariable String projectName) {
        return projectService.getProjectResponseByName(projectName);
    }

    @PostMapping("/{projectName}/donate")
    public ResponseEntity<?> donateToProject(@PathVariable String projectName,
                                          @RequestBody DonateRequest donateRequest,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        projectService.donateToProject(projectName, donateRequest);
        User donater = userService.findUserByEmail(userDetails.getUsername());
        Project project = projectService.getProjectByName(projectName);
        donationService.saveDonation(donater, project, donateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectName}/edit")
    public ResponseEntity<?> editProject(@PathVariable String projectName,
                                         @RequestBody DonateRequest donateRequest,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        // TODO
        return ResponseEntity.ok().build();
    }
}
