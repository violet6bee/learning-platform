package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.SubmissionRequestDTO;
import shvalieva.learning_platform.dto.SubmissionResponseDTO;
import shvalieva.learning_platform.entity.SubmissionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubmissionMapper {

    @Mapping(target = "assignmentId", source = "assignment.id")
    @Mapping(target = "studentId", source = "student.id")
    SubmissionResponseDTO toDto(SubmissionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    @Mapping(target = "student", ignore = true)
    SubmissionEntity fromRequestDto(SubmissionRequestDTO dto);
}
