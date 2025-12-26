package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.SubmissionRequestDTO;
import shvalieva.learning_platform.dto.SubmissionResponseDTO;
import shvalieva.learning_platform.entity.AssignmentEntity;
import shvalieva.learning_platform.entity.SubmissionEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.mapper.SubmissionMapper;
import shvalieva.learning_platform.repository.AssignmentRepository;
import shvalieva.learning_platform.repository.SubmissionRepository;
import shvalieva.learning_platform.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubmissionMapper submissionMapper;

    @InjectMocks
    private SubmissionService submissionService;

    @Test
    void getById_success() {
        SubmissionEntity entity = new SubmissionEntity();
        SubmissionResponseDTO dto = new SubmissionResponseDTO();

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(submissionMapper.toDto(entity)).thenReturn(dto);

        SubmissionResponseDTO result = submissionService.getById(1L);

        assertNotNull(result);
        verify(submissionRepository).findById(1L);
        verify(submissionMapper).toDto(entity);
    }

    @Test
    void getById_notFound() {
        when(submissionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> submissionService.getById(1L));
    }

    @Test
    void getByAssignment() {
        SubmissionEntity e1 = new SubmissionEntity();
        SubmissionEntity e2 = new SubmissionEntity();

        when(submissionRepository.findAllByAssignmentId(1L))
                .thenReturn(List.of(e1, e2));
        when(submissionMapper.toDto(any()))
                .thenReturn(new SubmissionResponseDTO());

        List<SubmissionResponseDTO> result =
                submissionService.getByAssignment(1L);

        assertEquals(2, result.size());
        verify(submissionRepository).findAllByAssignmentId(1L);
    }

    @Test
    void getByStudent() {
        when(submissionRepository.findAllByStudentId(2L))
                .thenReturn(List.of(new SubmissionEntity()));
        when(submissionMapper.toDto(any()))
                .thenReturn(new SubmissionResponseDTO());

        List<SubmissionResponseDTO> result =
                submissionService.getByStudent(2L);

        assertEquals(1, result.size());
    }

    @Test
    void create_success() {
        SubmissionRequestDTO request = new SubmissionRequestDTO();
        request.setAssignmentId(1L);
        request.setStudentId(2L);

        AssignmentEntity assignment = new AssignmentEntity();
        UserEntity student = new UserEntity();
        SubmissionEntity entity = new SubmissionEntity();
        SubmissionResponseDTO responseDTO = new SubmissionResponseDTO();

        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(userRepository.findById(2L)).thenReturn(Optional.of(student));
        when(submissionMapper.fromRequestDto(request)).thenReturn(entity);
        when(submissionRepository.save(entity)).thenReturn(entity);
        when(submissionMapper.toDto(entity)).thenReturn(responseDTO);

        SubmissionResponseDTO result = submissionService.create(request);

        assertNotNull(result);
        verify(submissionRepository).save(entity);
    }

    @Test
    void submitAssignment_success() {
        SubmissionRequestDTO request = new SubmissionRequestDTO();
        request.setContent("answer");

        AssignmentEntity assignment = new AssignmentEntity();
        UserEntity student = new UserEntity();
        SubmissionEntity entity = new SubmissionEntity();
        SubmissionResponseDTO responseDTO = new SubmissionResponseDTO();

        when(submissionRepository.existsByAssignmentIdAndStudentId(1L, 2L))
                .thenReturn(false);
        when(assignmentRepository.findById(1L))
                .thenReturn(Optional.of(assignment));
        when(userRepository.findById(2L))
                .thenReturn(Optional.of(student));
        when(submissionMapper.fromRequestDto(request))
                .thenReturn(entity);
        when(submissionRepository.save(entity))
                .thenReturn(entity);
        when(submissionMapper.toDto(entity))
                .thenReturn(responseDTO);

        SubmissionResponseDTO result =
                submissionService.submitAssignment(2L, 1L, request);

        assertNotNull(result);
        assertNotNull(entity.getSubmittedAt());
        verify(submissionRepository).save(entity);
    }

    @Test
    void submitAssignment_alreadyExists() {
        when(submissionRepository.existsByAssignmentIdAndStudentId(1L, 2L))
                .thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> submissionService.submitAssignment(
                        2L, 1L, new SubmissionRequestDTO()));
    }

    @Test
    void gradeSubmission_success() {
        SubmissionEntity entity = new SubmissionEntity();
        SubmissionResponseDTO dto = new SubmissionResponseDTO();

        when(submissionRepository.findById(1L))
                .thenReturn(Optional.of(entity));
        when(submissionMapper.toDto(entity))
                .thenReturn(dto);

        SubmissionResponseDTO result =
                submissionService.gradeSubmission(1L, (byte) 95, "Good");

        assertNotNull(result);
        assertEquals(95, entity.getScore());
        assertEquals("Good", entity.getFeedback());
    }

    @Test
    void delete_success() {
        submissionService.delete(1L);
        verify(submissionRepository).deleteById(1L);
    }
}
