package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.auth.ApplicationUser;
import ru.cft.croudfounding.payload.request.DonateRequest;
import ru.cft.croudfounding.payload.request.ProjectInfoRequest;
import ru.cft.croudfounding.payload.response.ProjectInfoResponse;
import ru.cft.croudfounding.payload.response.ProjectsUnitPreviewResponse;
import ru.cft.croudfounding.repository.ProjectRepository;
import ru.cft.croudfounding.repository.mapper.CrowdfundingMapper;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.repository.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final CrowdfundingMapper mapper;

    public ProjectInfoResponse saveProject(ProjectInfoRequest newProject) {
        ApplicationUser principal = (ApplicationUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByEmail(principal.getUsername());
        Project project = mapper.importProject(newProject);
        project.setParent(user);
        project = projectRepository.save(project);
        return mapper.exportProject(project);
    }

    public List<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).getContent();
    }

    public Project getProjectByName(String name) {
        return projectRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }

    public ProjectInfoResponse getProjectResponseByName(String name) {
        var project = getProjectByName( name);
        return mapper.exportProject(project);
    }

    public ProjectsUnitPreviewResponse findAllByParent(Long id) {
        User user = userService.findUserById(id);
        var projects = projectRepository.findAllByParent(user);
        var projectDTO = projects.stream()
                .map(mapper::exportProjectPreview)
                .collect(Collectors.toList());
        return new ProjectsUnitPreviewResponse(projectDTO);
    }

    public void donateToProject(String projectName, DonateRequest donateRequest) {
        Project project = getProjectByName(projectName);
        project.setCashDonated(project.getCashDonated() + donateRequest.getDonationAmount());
        projectRepository.save(project);
    }
}
