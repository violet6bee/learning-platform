package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.AnswerOptionRequestDTO;
import shvalieva.learning_platform.dto.AnswerOptionResponseDTO;
import shvalieva.learning_platform.entity.AnswerOptionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnswerOptionMapper {

    @Mapping(target = "questionId", source = "question.id")
    AnswerOptionResponseDTO toDto(AnswerOptionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    AnswerOptionEntity fromRequestDto(AnswerOptionRequestDTO dto);
}
