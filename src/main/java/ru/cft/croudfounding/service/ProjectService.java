package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.croudfounding.model.ProjectUnitDTO;
import ru.cft.croudfounding.repository.ProjectRepository;
import ru.cft.croudfounding.repository.mapper.croudfoundingMapper;
import ru.cft.croudfounding.repository.model.Project;

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
}
