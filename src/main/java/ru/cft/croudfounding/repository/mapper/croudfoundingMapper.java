package ru.cft.croudfounding.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cft.croudfounding.model.UserDTO;
import ru.cft.croudfounding.repository.model.UserEntity;

@Mapper(componentModel = "spring", imports = UserDTO.class)
public interface croudfoundingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    UserEntity importUser(UserDTO userDTO);

}
