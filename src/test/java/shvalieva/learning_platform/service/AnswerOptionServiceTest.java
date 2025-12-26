package shvalieva.learning_platform.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.AnswerOptionRequestDTO;
import shvalieva.learning_platform.dto.AnswerOptionResponseDTO;
import shvalieva.learning_platform.entity.AnswerOptionEntity;
import shvalieva.learning_platform.entity.QuestionEntity;
import shvalieva.learning_platform.repository.AnswerOptionRepository;
import shvalieva.learning_platform.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerOptionServiceTest {

    @Mock
    private AnswerOptionRepository answerOptionRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private AnswerOptionService answerOptionService;

    @Test
    void getById_success() {
        QuestionEntity question = new QuestionEntity();
        question.setId(1L);

        AnswerOptionEntity option = new AnswerOptionEntity(question, "Option A", true);
        option.setId(10L);

        when(answerOptionRepository.findById(10L))
                .thenReturn(Optional.of(option));

        AnswerOptionResponseDTO result = answerOptionService.getById(10L);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Option A", result.getText());
        assertTrue(result.isCorrect());
        assertEquals(1L, result.getQuestionId());

        verify(answerOptionRepository).findById(10L);
    }

    @Test
    void getById_notFound() {
        when(answerOptionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> answerOptionService.getById(1L));
    }

    @Test
    void getByQuestionId_success() {
        QuestionEntity question = new QuestionEntity();
        question.setId(1L);

        AnswerOptionEntity option1 = new AnswerOptionEntity(question, "A", true);
        AnswerOptionEntity option2 = new AnswerOptionEntity(question, "B", false);

        when(answerOptionRepository.findByQuestionId(1L))
                .thenReturn(List.of(option1, option2));

        List<AnswerOptionResponseDTO> result =
                answerOptionService.getByQuestionId(1L);

        assertEquals(2, result.size());
        verify(answerOptionRepository).findByQuestionId(1L);
    }

    @Test
    void createAnswerOption_success() {
        QuestionEntity question = new QuestionEntity();
        question.setId(1L);

        AnswerOptionRequestDTO request = new AnswerOptionRequestDTO();
        request.setQuestionId(1L);
        request.setText("Option A");
        request.isCorrect();

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(question));

        when(answerOptionRepository.save(any(AnswerOptionEntity.class)))
                .thenAnswer(invocation -> {
                    AnswerOptionEntity entity = invocation.getArgument(0);
                    entity.setId(100L);
                    return entity;
                });

        AnswerOptionResponseDTO result =
                answerOptionService.createAnswerOption(request);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Option A", result.getText());
        assertTrue(result.isCorrect());
        assertEquals(1L, result.getQuestionId());

        verify(questionRepository).findById(1L);
        verify(answerOptionRepository).save(any());
    }

    @Test
    void createAnswerOption_questionNotFound() {
        AnswerOptionRequestDTO request = new AnswerOptionRequestDTO();
        request.setQuestionId(1L);

        when(questionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> answerOptionService.createAnswerOption(request));
    }

    @Test
    void deleteAnswerOption_success() {
        AnswerOptionEntity option = new AnswerOptionEntity();
        option.setId(1L);

        when(answerOptionRepository.findById(1L))
                .thenReturn(Optional.of(option));

        doNothing().when(answerOptionRepository).delete(option);

        answerOptionService.deleteAnswerOption(1L);

        verify(answerOptionRepository).delete(option);
    }

    @Test
    void deleteAnswerOption_notFound() {
        when(answerOptionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> answerOptionService.deleteAnswerOption(1L));
    }
}
