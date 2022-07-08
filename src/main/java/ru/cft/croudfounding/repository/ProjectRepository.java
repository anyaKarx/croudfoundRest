package ru.cft.croudfounding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.repository.model.User;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {



}
