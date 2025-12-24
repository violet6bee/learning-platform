package shvalieva.learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import shvalieva.learning_platform.dto.ModuleRequestDTO;
import shvalieva.learning_platform.dto.ModuleResponseDTO;
import shvalieva.learning_platform.entity.ModuleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ModuleMapper {

    ModuleResponseDTO toDto(ModuleEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    ModuleEntity fromRequestDto(ModuleRequestDTO dto);
}
