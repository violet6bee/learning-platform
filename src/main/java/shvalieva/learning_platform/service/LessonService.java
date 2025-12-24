package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.LessonRequestDTO;
import shvalieva.learning_platform.dto.LessonResponseDTO;
import shvalieva.learning_platform.entity.LessonEntity;
import shvalieva.learning_platform.entity.ModuleEntity;
import shvalieva.learning_platform.mapper.LessonMapper;
import shvalieva.learning_platform.repository.LessonRepository;
import shvalieva.learning_platform.repository.ModuleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonService {

    private LessonRepository lessonRepository;
    private LessonMapper lessonMapper;
    private ModuleRepository moduleRepository;

    @Transactional(readOnly = true)
    public LessonResponseDTO getLessonById(Long id) {
        LessonEntity entity = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Урок с идентифкатором " + id + " не найден."));
        return lessonMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<LessonResponseDTO> getAllLessons() {
        return lessonRepository.findAll().stream().map(lessonMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public LessonResponseDTO createLesson(LessonRequestDTO dto) {
        ModuleEntity module = moduleRepository.findById(dto.getModuleId())
                .orElseThrow(() -> new EntityNotFoundException("Модуль не найден"));
        LessonEntity entity = lessonMapper.fromRequestDto(dto);
        entity.setModule(module);
        LessonEntity saved = lessonRepository.save(entity);
        return lessonMapper.toDto(saved);
    }

    @Transactional
    public LessonResponseDTO updateLesson(Long id, LessonRequestDTO dto) {
        LessonEntity entity = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Урок не найден"));
        entity.setTitle(dto.getTitle());
        entity.setVideoUrl(dto.getVideoUrl());
        return lessonMapper.toDto(entity);
    }

}
