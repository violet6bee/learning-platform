package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.LessonRequestDTO;
import shvalieva.learning_platform.dto.LessonResponseDTO;
import shvalieva.learning_platform.entity.LessonEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper {
    @Mapping(target = "moduleId", source = "module.id")
    LessonResponseDTO toDto(LessonEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    LessonEntity fromRequestDto(LessonRequestDTO dto);
}
