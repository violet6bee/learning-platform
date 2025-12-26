package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.QuizRequestDTO;
import shvalieva.learning_platform.dto.QuizResponseDTO;
import shvalieva.learning_platform.entity.CourseEntity;
import shvalieva.learning_platform.entity.QuizEntity;
import shvalieva.learning_platform.mapper.QuizMapper;
import shvalieva.learning_platform.repository.CourseRepository;
import shvalieva.learning_platform.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private QuizMapper quizMapper;

    @InjectMocks
    private QuizService quizService;

    @Test
    void getQuizById_success() {
        QuizEntity entity = new QuizEntity();
        QuizResponseDTO dto = new QuizResponseDTO();

        when(quizRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(quizMapper.toDto(entity)).thenReturn(dto);

        QuizResponseDTO result = quizService.getQuizById(1L);

        assertNotNull(result);
        verify(quizRepository).findById(1L);
        verify(quizMapper).toDto(entity);
    }

    @Test
    void getQuizById_notFound() {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> quizService.getQuizById(1L));
    }

    @Test
    void getAllQuizzes_success() {
        QuizEntity q1 = new QuizEntity();
        QuizEntity q2 = new QuizEntity();

        when(quizRepository.findAll()).thenReturn(List.of(q1, q2));
        when(quizMapper.toDto(any(QuizEntity.class))).thenReturn(new QuizResponseDTO());

        List<QuizResponseDTO> result = quizService.getAllQuizzes();

        assertEquals(2, result.size());
        verify(quizRepository).findAll();
        verify(quizMapper, times(2)).toDto(any(QuizEntity.class));
    }

    @Test
    void createQuiz_success() {
        QuizRequestDTO requestDTO = new QuizRequestDTO();
        requestDTO.setCourseId(1L);
        requestDTO.setTitle("Test quiz");

        CourseEntity course = new CourseEntity();
        course.setId(1L);

        QuizEntity entity = new QuizEntity();
        QuizEntity savedEntity = new QuizEntity();
        QuizResponseDTO responseDTO = new QuizResponseDTO();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(quizMapper.fromRequestDto(requestDTO)).thenReturn(entity);
        when(quizRepository.save(entity)).thenReturn(savedEntity);
        when(quizMapper.toDto(savedEntity)).thenReturn(responseDTO);

        QuizResponseDTO result = quizService.createQuiz(requestDTO);

        assertNotNull(result);
        verify(courseRepository).findById(1L);
        verify(quizMapper).fromRequestDto(requestDTO);
        verify(quizRepository).save(entity);
        verify(quizMapper).toDto(savedEntity);
    }

    @Test
    void createQuiz_courseNotFound() {
        QuizRequestDTO requestDTO = new QuizRequestDTO();
        requestDTO.setCourseId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> quizService.createQuiz(requestDTO));
    }

    @Test
    void deleteQuiz_success() {
        doNothing().when(quizRepository).deleteById(1L);

        quizService.deleteQuiz(1L);

        verify(quizRepository).deleteById(1L);
    }
}
