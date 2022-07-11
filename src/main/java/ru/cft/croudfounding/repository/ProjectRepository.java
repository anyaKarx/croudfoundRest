package ru.cft.croudfounding.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.cft.croudfounding.repository.model.Project;

import java.util.Optional;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

    Optional<Project> findByName(String name);
}
