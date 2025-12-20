package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shvalieva.learning_platform.dto.CategoryRequestDTO;
import shvalieva.learning_platform.dto.CategoryResponseDTO;
import shvalieva.learning_platform.dto.TagResponseDTO;
import shvalieva.learning_platform.entity.CategoryEntity;
import shvalieva.learning_platform.entity.TagEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toDto(CategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    CategoryEntity fromRequestDto(CategoryRequestDTO dto);

    @Mapping(target = "courses", ignore = true)
    TagEntity toEntity(TagResponseDTO dto);

}
