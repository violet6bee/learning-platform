package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shvalieva.learning_platform.dto.UserRequestDTO;
import shvalieva.learning_platform.dto.UserResponseDTO;
import shvalieva.learning_platform.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDto(UserEntity user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "profileEntity", ignore = true)
    @Mapping(target = "coursesTaught", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    @Mapping(target = "quizSubmissions", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    UserEntity fromRequestDto(UserRequestDTO dto);
}
