package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.QuestionRequestDTO;
import shvalieva.learning_platform.dto.QuestionResponseDTO;
import shvalieva.learning_platform.entity.QuestionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper {

    @Mapping(target = "quizId", source = "quiz.id")
    QuestionResponseDTO toDto(QuestionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "answers", ignore = true)
    QuestionEntity fromRequestDto(QuestionRequestDTO dto);
}
