package ru.cft.croudfounding.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cft.croudfounding.model.ProjectUnitDTO;
import ru.cft.croudfounding.model.UserDTO;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.repository.model.User;

@Mapper(componentModel = "spring", imports = UserDTO.class)
public interface croudfoundingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    User importUser(UserDTO userDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserDTO exportUser(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Project importProject(ProjectUnitDTO projectUnitDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProjectUnitDTO exportProject(Project project);

}
