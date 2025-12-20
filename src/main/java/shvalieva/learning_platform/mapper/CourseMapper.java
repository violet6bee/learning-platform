package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.CourseRequestDTO;
import shvalieva.learning_platform.dto.CourseResponseDTO;
import shvalieva.learning_platform.dto.TagResponseDTO;
import shvalieva.learning_platform.entity.CourseEntity;
import shvalieva.learning_platform.entity.TagEntity;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    CourseResponseDTO toDto(CourseEntity entity);

    Set<TagResponseDTO> toTagDtoSet(Set<TagEntity> tags);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "quizzes", ignore = true)
    @Mapping(target = "courseReviews", ignore = true)
    @Mapping(target = "tags", ignore = true)
    CourseEntity fromRequestDto(CourseRequestDTO dto);
}