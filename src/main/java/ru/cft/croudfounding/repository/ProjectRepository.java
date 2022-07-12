package ru.cft.croudfounding.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.repository.model.User;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

    Optional<Project> findByName(String name);
    List<Project> findAllByParent(User parent);
}
