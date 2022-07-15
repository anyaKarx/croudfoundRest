package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.payload.request.ProjectInfoRequest;
import ru.cft.croudfounding.payload.request.UpdateProjectInfoRequest;
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

    public ProjectInfoResponse createProject(ProjectInfoRequest newProject) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findUserByEmail(auth.getName());
        Project project = mapper.importProject(newProject);
        project.setParent(user);
        project.setCollectedAmount(0L);
        project = projectRepository.save(project);
        ProjectInfoResponse projectInfoResponse = mapper.exportProject(project);
        projectInfoResponse.setParentName(user.getName());
        return projectInfoResponse;
    }

    public List<ProjectInfoResponse> findAll(Pageable pageable) {
        List<Project> projects = projectRepository.findAll(pageable).getContent();
        return projects.stream()
                .map(mapper::exportProject)
                .collect(Collectors.toList());
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Project with id=%d not found", id)));
    }

    public ProjectInfoResponse getProjectResponseById(Long id ) {
        Project project = getProjectById(id);
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

    public void donateToProject(Long id, Long donateAmount) {
        Project project = getProjectById(id);
        project.setCollectedAmount(project.getCollectedAmount() + donateAmount);
        projectRepository.save(project);
    }

    public ProjectInfoResponse updateProjectByName(Long id, UpdateProjectInfoRequest updatedProject) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Project project = this.getProjectById(id);

        if (!project.getParent().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No rights to modify this project");
        }

        project.setEndDate(updatedProject.getEndDate());
        project.setRequiredAmount(updatedProject.getRequiredAmount());
        project.setDescription(updatedProject.getDescription());
        project = projectRepository.save(project);

        return mapper.exportProject(project);
    }
}
