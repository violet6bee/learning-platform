package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.QuestionRequestDTO;
import shvalieva.learning_platform.dto.QuestionResponseDTO;
import shvalieva.learning_platform.entity.QuestionEntity;
import shvalieva.learning_platform.entity.QuizEntity;
import shvalieva.learning_platform.enums.TypeChoiceEnum;
import shvalieva.learning_platform.mapper.QuestionMapper;
import shvalieva.learning_platform.repository.QuestionRepository;
import shvalieva.learning_platform.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void getById_success() {
        QuestionEntity entity = new QuestionEntity();
        QuestionResponseDTO dto = new QuestionResponseDTO();

        when(questionRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(questionMapper.toDto(entity)).thenReturn(dto);

        QuestionResponseDTO result = questionService.getById(1L);

        assertNotNull(result);
        verify(questionRepository).findById(1L);
        verify(questionMapper).toDto(entity);
    }

    @Test
    void getById_notFound() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> questionService.getById(1L));
    }

    @Test
    void getByQuizId_success() {
        QuestionEntity q1 = new QuestionEntity();
        QuestionEntity q2 = new QuestionEntity();

        QuestionResponseDTO dto1 = new QuestionResponseDTO();
        QuestionResponseDTO dto2 = new QuestionResponseDTO();

        when(questionRepository.findByQuizId(1L)).thenReturn(List.of(q1, q2));
        when(questionMapper.toDto(q1)).thenReturn(dto1);
        when(questionMapper.toDto(q2)).thenReturn(dto2);

        List<QuestionResponseDTO> result = questionService.getByQuizId(1L);

        assertEquals(2, result.size());
        verify(questionRepository).findByQuizId(1L);
    }

    @Test
    void createQuestion_success() {
        QuestionRequestDTO request = new QuestionRequestDTO();
        request.setQuizId(1L);
        request.setText("Question text");
        request.setTypeChoices(TypeChoiceEnum.SINGLE_CHOICE);
        request.setCorrect(true);

        QuizEntity quiz = new QuizEntity();
        quiz.setId(1L);

        QuestionEntity entity = new QuestionEntity();
        QuestionEntity savedEntity = new QuestionEntity();
        QuestionResponseDTO responseDTO = new QuestionResponseDTO();

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionMapper.fromRequestDto(request)).thenReturn(entity);
        when(questionRepository.save(entity)).thenReturn(savedEntity);
        when(questionMapper.toDto(savedEntity)).thenReturn(responseDTO);

        QuestionResponseDTO result = questionService.createQuestion(request);

        assertNotNull(result);
        verify(quizRepository).findById(1L);
        verify(questionRepository).save(entity);
        verify(questionMapper).toDto(savedEntity);
    }

    @Test
    void createQuestion_quizNotFound() {
        QuestionRequestDTO request = new QuestionRequestDTO();
        request.setQuizId(1L);

        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> questionService.createQuestion(request));
    }

    @Test
    void deleteQuestion_success() {
        when(questionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(questionRepository).deleteById(1L);

        questionService.deleteQuestion(1L);

        verify(questionRepository).deleteById(1L);
    }

    @Test
    void deleteQuestion_notFound() {
        when(questionRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> questionService.deleteQuestion(1L));
    }
}
