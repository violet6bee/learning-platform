package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.AssignmentRequestDTO;
import shvalieva.learning_platform.dto.AssignmentResponseDTO;
import shvalieva.learning_platform.entity.AssignmentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssignmentMapper {

    @Mapping(target = "lessonId", source = "lesson.id")
    AssignmentResponseDTO toDto(AssignmentEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    AssignmentEntity fromRequestDto(AssignmentRequestDTO dto);
}
