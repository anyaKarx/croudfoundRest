package ru.cft.croudfounding.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cft.croudfounding.payload.request.ProjectInfoRequest;
import ru.cft.croudfounding.payload.request.SignupRequest;
import ru.cft.croudfounding.payload.response.ProjectUnitPreviewResponse;
import ru.cft.croudfounding.payload.response.ProjectInfoResponse;
import ru.cft.croudfounding.payload.response.UserInfoResponse;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.repository.model.User;

@Mapper(componentModel = "spring", imports = UserInfoResponse.class)
public interface CrowdfundingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "projects", ignore = true)
    User importUser(SignupRequest signupRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    UserInfoResponse exportUser(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "collectedAmount", ignore = true)
    @Mapping(target = "parent", ignore = true)
    Project importProject(ProjectInfoRequest projectInfoResponse);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "parentId", expression = "java(project.getParent().getId())")
    @Mapping(target = "parentName", expression = "java(project.getParent().getName())")
    @Mapping(target = "date", source = "date")
    ProjectInfoResponse exportProject(Project project);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "parentName", ignore = true)
    ProjectUnitPreviewResponse exportProjectPreview(Project project);
}
