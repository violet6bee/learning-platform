package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shvalieva.learning_platform.dto.TagRequestDTO;
import shvalieva.learning_platform.dto.TagResponseDTO;
import shvalieva.learning_platform.entity.TagEntity;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagResponseDTO toDto(TagEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    TagEntity fromRequestDto(TagRequestDTO dto);

    @Mapping(target = "courses", ignore = true)
    TagEntity toEntity(TagResponseDTO dto);


}
