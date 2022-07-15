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

//    public ProjectInfoResponse updateProject(ProjectInfoRequest project) {
//
//    }

    public List<ProjectInfoResponse> findAll(Pageable pageable) {
        List<Project> projects = projectRepository.findAll(pageable).getContent();
        return projects.stream()
                .map(mapper::exportProject)
                .collect(Collectors.toList());
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
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

    public ProjectInfoResponse updateProjectByName(Long id, UpdateProjectInfoRequest updateProjectInfoRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Project project = projectRepository.findById(id).orElseThrow(
                ()-> {throw new RuntimeException();
                });
        if (!project.getParent().getEmail().equals(user.getEmail()))
            throw new RuntimeException("нарушение прав доступа");
        project.setDescription(updateProjectInfoRequest.getDescription());
        project.setDate(updateProjectInfoRequest.getStartDate());
        project.setEndDate(updateProjectInfoRequest.getEndDate());
        project.setRequiredAmount(updateProjectInfoRequest.getRequiredAmount());
        project = projectRepository.save(project);
        var projectInfo =  mapper.exportProject(project);
        projectInfo.setParentName(user.getName());
        return projectInfo;
    }
}
