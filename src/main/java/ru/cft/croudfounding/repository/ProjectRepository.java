package ru.cft.croudfounding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.croudfounding.repository.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
