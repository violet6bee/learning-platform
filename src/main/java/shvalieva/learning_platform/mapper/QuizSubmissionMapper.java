package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.QuizSubmissionRequestDTO;
import shvalieva.learning_platform.dto.QuizSubmissionResponseDTO;
import shvalieva.learning_platform.entity.QuizSubmissionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizSubmissionMapper {

    @Mapping(target = "quizId", source = "quiz.id")
    @Mapping(target = "studentId", source = "student.id")
    QuizSubmissionResponseDTO toDto(QuizSubmissionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "student", ignore = true)
    QuizSubmissionEntity fromRequestDto(QuizSubmissionRequestDTO dto);
}
