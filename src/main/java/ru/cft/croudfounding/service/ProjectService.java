package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.auth.ApplicationUser;
import ru.cft.croudfounding.model.DonateRequest;
import ru.cft.croudfounding.model.ProjectUnitDTO;
import ru.cft.croudfounding.model.ProjectUnitPreviewResponseDTO;
import ru.cft.croudfounding.repository.ProjectRepository;
import ru.cft.croudfounding.repository.mapper.croudfoundingMapper;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.repository.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final croudfoundingMapper mapper;

    public ProjectUnitDTO saveProject(ProjectUnitDTO newProject) {
        ApplicationUser principal = (ApplicationUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByEmail(principal.getUsername());
        Project project = mapper.importProject(newProject);
        if (!project.getParent().getEmail().equals(user.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Создание проекта не под своим аккаунтом");
        project = projectRepository.save(project);
        newProject.setId(project.getId());
        return newProject;
    }

    public List<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).getContent();
    }

    public Project getProjectByName(String name) {
        Project project = projectRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        return project;
    }

    public ProjectUnitPreviewResponseDTO findAllByParent(String email) {
        User user = userService.findUserByEmail(email);
        var projects = projectRepository.findAllByParent(user);
        var projectDTO = projects.stream()
                .map(project -> mapper.exportProjectPreview(project))
                .collect(Collectors.toList());
        return new ProjectUnitPreviewResponseDTO(projectDTO);
    }

    public void donateToProject(String projectName, DonateRequest donateRequest) {
        Project project = getProjectByName(projectName);
        project.setCashDonated(project.getCashDonated() + donateRequest.getDonationAmount());
        projectRepository.save(project);
    }
}
