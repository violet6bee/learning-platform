package shvalieva.learning_platform.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.ModuleRequestDTO;
import shvalieva.learning_platform.dto.ModuleResponseDTO;
import shvalieva.learning_platform.entity.CourseEntity;
import shvalieva.learning_platform.entity.ModuleEntity;
import shvalieva.learning_platform.mapper.ModuleMapper;
import shvalieva.learning_platform.repository.CourseRepository;
import shvalieva.learning_platform.repository.ModuleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ModuleService {

    private ModuleRepository moduleRepository;
    private ModuleMapper moduleMapper;
    private CourseRepository courseRepository;

    public ModuleResponseDTO getModuleById(Long id) {
        ModuleEntity entity = moduleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Модуль с id: " + id + " не найден"));
        return moduleMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ModuleResponseDTO> getAllModulesByCourseId(Long courseID) {
        return moduleRepository.findByCourseId(courseID).stream()
                .map(moduleMapper::toDto)
                .toList();
    }

    @Transactional
    public ModuleResponseDTO createModule(ModuleRequestDTO dto) {
        CourseEntity course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Курс с id " + dto.getCourseId() + " не найден"));
        ModuleEntity entity = moduleMapper.fromRequestDto(dto);
        entity.setCourse(course);
        entity = moduleRepository.save(entity);
        return moduleMapper.toDto(entity);
    }

    @Transactional
    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
}
