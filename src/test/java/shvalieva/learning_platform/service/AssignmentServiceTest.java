package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.AssignmentRequestDTO;
import shvalieva.learning_platform.dto.AssignmentResponseDTO;
import shvalieva.learning_platform.entity.AssignmentEntity;
import shvalieva.learning_platform.entity.LessonEntity;
import shvalieva.learning_platform.mapper.AssignmentMapper;
import shvalieva.learning_platform.repository.AssignmentRepository;
import shvalieva.learning_platform.repository.LessonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private AssignmentMapper assignmentMapper;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void getById_success() {
        AssignmentEntity entity = new AssignmentEntity();
        AssignmentResponseDTO responseDTO = new AssignmentResponseDTO();

        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(assignmentMapper.toDto(entity)).thenReturn(responseDTO);

        AssignmentResponseDTO result = assignmentService.getById(1L);

        assertNotNull(result);
        verify(assignmentRepository).findById(1L);
        verify(assignmentMapper).toDto(entity);
    }

    @Test
    void getById_notFound() {
        when(assignmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> assignmentService.getById(1L));
    }

    @Test
    void getByLesson_success() {
        AssignmentEntity entity1 = new AssignmentEntity();
        AssignmentEntity entity2 = new AssignmentEntity();

        AssignmentResponseDTO dto1 = new AssignmentResponseDTO();
        AssignmentResponseDTO dto2 = new AssignmentResponseDTO();

        when(assignmentRepository.findAllByLessonId(1L))
                .thenReturn(List.of(entity1, entity2));
        when(assignmentMapper.toDto(entity1)).thenReturn(dto1);
        when(assignmentMapper.toDto(entity2)).thenReturn(dto2);

        List<AssignmentResponseDTO> result =
                assignmentService.getByLesson(1L);

        assertEquals(2, result.size());
        verify(assignmentRepository).findAllByLessonId(1L);
    }

    @Test
    void create_success() {
        AssignmentRequestDTO requestDTO = new AssignmentRequestDTO();
        requestDTO.setLessonId(1L);
        requestDTO.setTitle("HW");
        requestDTO.setDescription("Homework");
        requestDTO.setDueDate(LocalDateTime.now());
        requestDTO.setMaxScore((byte) 10);

        LessonEntity lesson = new LessonEntity();
        AssignmentEntity entity = new AssignmentEntity();
        AssignmentEntity saved = new AssignmentEntity();
        AssignmentResponseDTO responseDTO = new AssignmentResponseDTO();

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(assignmentMapper.fromRequestDto(requestDTO)).thenReturn(entity);
        when(assignmentRepository.save(entity)).thenReturn(saved);
        when(assignmentMapper.toDto(saved)).thenReturn(responseDTO);

        AssignmentResponseDTO result =
                assignmentService.create(requestDTO);

        assertNotNull(result);
        verify(lessonRepository).findById(1L);
        verify(assignmentRepository).save(entity);
        verify(assignmentMapper).toDto(saved);
    }

    @Test
    void create_lessonNotFound() {
        AssignmentRequestDTO requestDTO = new AssignmentRequestDTO();
        requestDTO.setLessonId(1L);

        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> assignmentService.create(requestDTO));
    }

    @Test
    void delete_success() {
        doNothing().when(assignmentRepository).deleteById(1L);

        assignmentService.delete(1L);

        verify(assignmentRepository).deleteById(1L);
    }
}
