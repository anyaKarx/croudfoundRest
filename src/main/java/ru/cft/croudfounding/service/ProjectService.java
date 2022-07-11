package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.model.ProjectUnitDTO;
import ru.cft.croudfounding.repository.ProjectRepository;
import ru.cft.croudfounding.repository.mapper.croudfoundingMapper;
import ru.cft.croudfounding.repository.model.Project;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final croudfoundingMapper mapper;

    public ProjectUnitDTO saveProject(ProjectUnitDTO newProject) {
        Project project = mapper.importProject(newProject);
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
}
