package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.QuizSubmissionRequestDTO;
import shvalieva.learning_platform.dto.QuizSubmissionResponseDTO;
import shvalieva.learning_platform.entity.QuizEntity;
import shvalieva.learning_platform.entity.QuizSubmissionEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.mapper.QuizSubmissionMapper;
import shvalieva.learning_platform.repository.QuizRepository;
import shvalieva.learning_platform.repository.QuizSubmissionRepository;
import shvalieva.learning_platform.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizSubmissionServiceTest {

    @Mock
    private QuizSubmissionRepository quizSubmissionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuizSubmissionMapper quizSubmissionMapper;

    @InjectMocks
    private QuizSubmissionService quizSubmissionService;

    @Test
    void getById_success() {
        QuizSubmissionEntity entity = new QuizSubmissionEntity();
        QuizSubmissionResponseDTO dto = new QuizSubmissionResponseDTO();

        when(quizSubmissionRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(quizSubmissionMapper.toDto(entity)).thenReturn(dto);

        QuizSubmissionResponseDTO result = quizSubmissionService.getById(1L);

        assertNotNull(result);
        verify(quizSubmissionRepository).findById(1L);
        verify(quizSubmissionMapper).toDto(entity);
    }

    @Test
    void getById_notFound() {
        when(quizSubmissionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> quizSubmissionService.getById(1L));
    }

    @Test
    void getByQuiz_success() {
        QuizSubmissionEntity e1 = new QuizSubmissionEntity();
        QuizSubmissionEntity e2 = new QuizSubmissionEntity();

        when(quizSubmissionRepository.findAllByQuizId(1L))
                .thenReturn(Arrays.asList(e1, e2));
        when(quizSubmissionMapper.toDto(any()))
                .thenReturn(new QuizSubmissionResponseDTO());

        List<QuizSubmissionResponseDTO> result =
                quizSubmissionService.getByQuiz(1L);

        assertEquals(2, result.size());
        verify(quizSubmissionRepository).findAllByQuizId(1L);
    }

    @Test
    void getByStudent_success() {
        QuizSubmissionEntity e1 = new QuizSubmissionEntity();
        QuizSubmissionEntity e2 = new QuizSubmissionEntity();

        when(quizSubmissionRepository.findAllByStudentId(2L))
                .thenReturn(Arrays.asList(e1, e2));
        when(quizSubmissionMapper.toDto(any()))
                .thenReturn(new QuizSubmissionResponseDTO());

        List<QuizSubmissionResponseDTO> result =
                quizSubmissionService.getByStudent(2L);

        assertEquals(2, result.size());
        verify(quizSubmissionRepository).findAllByStudentId(2L);
    }

    @Test
    void create_success() {
        QuizSubmissionRequestDTO request = new QuizSubmissionRequestDTO();
        request.setQuizId(1L);
        request.setStudentId(2L);
        request.setScore((byte) 90);

        QuizEntity quiz = new QuizEntity();
        UserEntity student = new UserEntity();
        QuizSubmissionEntity entity = new QuizSubmissionEntity();
        QuizSubmissionEntity saved = new QuizSubmissionEntity();
        QuizSubmissionResponseDTO response = new QuizSubmissionResponseDTO();

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(userRepository.findById(2L)).thenReturn(Optional.of(student));
        when(quizSubmissionMapper.fromRequestDto(request)).thenReturn(entity);
        when(quizSubmissionRepository.save(entity)).thenReturn(saved);
        when(quizSubmissionMapper.toDto(saved)).thenReturn(response);

        QuizSubmissionResponseDTO result =
                quizSubmissionService.create(request);

        assertNotNull(result);
        verify(quizRepository).findById(1L);
        verify(userRepository).findById(2L);
        verify(quizSubmissionRepository).save(entity);
    }

    @Test
    void create_quizNotFound() {
        QuizSubmissionRequestDTO request = new QuizSubmissionRequestDTO();
        request.setQuizId(1L);
        request.setStudentId(2L);

        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> quizSubmissionService.create(request));
    }

    @Test
    void create_studentNotFound() {
        QuizSubmissionRequestDTO request = new QuizSubmissionRequestDTO();
        request.setQuizId(1L);
        request.setStudentId(2L);

        when(quizRepository.findById(1L)).thenReturn(Optional.of(new QuizEntity()));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> quizSubmissionService.create(request));
    }

    @Test
    void create_setsTakenAtIfNull() {
        QuizSubmissionRequestDTO request = new QuizSubmissionRequestDTO();
        request.setQuizId(1L);
        request.setStudentId(2L);
        request.setTakenAt(null);

        QuizSubmissionEntity entity = new QuizSubmissionEntity();

        when(quizRepository.findById(1L)).thenReturn(Optional.of(new QuizEntity()));
        when(userRepository.findById(2L)).thenReturn(Optional.of(new UserEntity()));
        when(quizSubmissionMapper.fromRequestDto(request)).thenReturn(entity);
        when(quizSubmissionRepository.save(entity)).thenAnswer(i -> i.getArgument(0));
        when(quizSubmissionMapper.toDto(any())).thenReturn(new QuizSubmissionResponseDTO());

        quizSubmissionService.create(request);

        assertNotNull(entity.getTakenAt());
    }

    @Test
    void delete_success() {
        doNothing().when(quizSubmissionRepository).deleteById(1L);

        quizSubmissionService.delete(1L);

        verify(quizSubmissionRepository).deleteById(1L);
    }
}
