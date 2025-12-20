package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shvalieva.learning_platform.dto.ProfileRequestDTO;
import shvalieva.learning_platform.dto.ProfileResponseDTO;
import shvalieva.learning_platform.entity.ProfileEntity;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "id", source = "id")
    ProfileResponseDTO toDto(ProfileEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    ProfileEntity fromRequestDto(ProfileRequestDTO dto);
}
