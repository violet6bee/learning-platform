package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.AssignmentRequestDTO;
import shvalieva.learning_platform.dto.AssignmentResponseDTO;
import shvalieva.learning_platform.entity.AssignmentEntity;
import shvalieva.learning_platform.entity.LessonEntity;
import shvalieva.learning_platform.mapper.AssignmentMapper;
import shvalieva.learning_platform.repository.AssignmentRepository;
import shvalieva.learning_platform.repository.LessonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AssignmentService {

    private AssignmentRepository assignmentRepository;
    private LessonRepository lessonRepository;
    private AssignmentMapper assignmentMapper;

    @Transactional(readOnly = true)
    public AssignmentResponseDTO getById(Long id) {
        AssignmentEntity entity = assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задание с id: " + id + " не найдено"));
        return assignmentMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<AssignmentResponseDTO> getByLesson(Long lessonId) {
        return assignmentRepository.findAllByLessonId(lessonId)
                .stream()
                .map(assignmentMapper::toDto)
                .toList();
    }

    @Transactional
    public AssignmentResponseDTO create(AssignmentRequestDTO dto) {
        LessonEntity lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new EntityNotFoundException("Урок с id " + dto.getLessonId() + " не найден"));

        AssignmentEntity entity = assignmentMapper.fromRequestDto(dto);
        entity.setLesson(lesson);
        AssignmentEntity saved = assignmentRepository.save(entity);
        return assignmentMapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        assignmentRepository.deleteById(id);
    }
}
