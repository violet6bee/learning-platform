package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.QuizRequestDTO;
import shvalieva.learning_platform.dto.QuizResponseDTO;
import shvalieva.learning_platform.entity.QuizEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizMapper {
    @Mapping(target = "courseId", source = "course.id")
    QuizResponseDTO toDto(QuizEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "quizSubmissions", ignore = true)
    QuizEntity fromRequestDto(QuizRequestDTO dto);
}
