package ru.cft.croudfounding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.croudfounding.repository.model.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
