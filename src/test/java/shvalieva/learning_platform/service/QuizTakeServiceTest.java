package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.QuizTakeRequestDTO;
import shvalieva.learning_platform.dto.QuizTakeResponseDTO;
import shvalieva.learning_platform.entity.*;
import shvalieva.learning_platform.repository.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizTakeServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerOptionRepository answerOptionRepository;

    @Mock
    private QuizSubmissionRepository quizSubmissionRepository;

    @InjectMocks
    private QuizTakeService quizTakeService;

    @Test
    void takeQuiz_success() {
        QuizEntity quiz = new QuizEntity();
        quiz.setId(1L);

        UserEntity student = new UserEntity();
        student.setId(2L);

        QuestionEntity q1 = new QuestionEntity();
        q1.setId(10L);

        QuestionEntity q2 = new QuestionEntity();
        q2.setId(20L);

        AnswerOptionEntity correctOption = new AnswerOptionEntity();
        correctOption.setId(100L);
        correctOption.setCorrect(true);

        AnswerOptionEntity wrongOption = new AnswerOptionEntity();
        wrongOption.setId(200L);
        wrongOption.setCorrect(false);

        QuizTakeRequestDTO dto = new QuizTakeRequestDTO();
        dto.setQuizId(1L);
        dto.setStudentId(2L);
        dto.setAnswers(Map.of(
                10L, 100L,
                20L, 200L
        ));

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(userRepository.findById(2L)).thenReturn(Optional.of(student));
        when(questionRepository.findAllByQuizId(1L)).thenReturn(List.of(q1, q2));
        when(answerOptionRepository.findById(100L)).thenReturn(Optional.of(correctOption));
        when(answerOptionRepository.findById(200L)).thenReturn(Optional.of(wrongOption));
        when(quizSubmissionRepository.save(any(QuizSubmissionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        QuizTakeResponseDTO response = quizTakeService.takeQuiz(dto);

        assertNotNull(response);
        assertEquals(1L, response.getQuizId());
        assertEquals(2L, response.getStudentId());
        assertEquals(2, response.getTotalQuestions());
        assertEquals(1, response.getCorrectAnswers());
        assertEquals(50, response.getScore());
        assertFalse(response.isPassed());

        verify(quizSubmissionRepository).save(any(QuizSubmissionEntity.class));
    }

    @Test
    void takeQuiz_quizNotFound() {
        QuizTakeRequestDTO dto = new QuizTakeRequestDTO();
        dto.setQuizId(1L);
        dto.setStudentId(2L);

        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> quizTakeService.takeQuiz(dto));
    }

    @Test
    void takeQuiz_studentNotFound() {
        QuizEntity quiz = new QuizEntity();
        quiz.setId(1L);

        QuizTakeRequestDTO dto = new QuizTakeRequestDTO();
        dto.setQuizId(1L);
        dto.setStudentId(2L);

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> quizTakeService.takeQuiz(dto));
    }

    @Test
    void takeQuiz_answerOptionNotFound() {
        QuizEntity quiz = new QuizEntity();
        quiz.setId(1L);

        UserEntity student = new UserEntity();
        student.setId(2L);

        QuestionEntity question = new QuestionEntity();
        question.setId(10L);

        QuizTakeRequestDTO dto = new QuizTakeRequestDTO();
        dto.setQuizId(1L);
        dto.setStudentId(2L);
        dto.setAnswers(Map.of(10L, 999L));

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(userRepository.findById(2L)).thenReturn(Optional.of(student));
        when(questionRepository.findAllByQuizId(1L)).thenReturn(List.of(question));
        when(answerOptionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> quizTakeService.takeQuiz(dto));
    }

    @Test
    void takeQuiz_noQuestions() {
        QuizEntity quiz = new QuizEntity();
        quiz.setId(1L);

        UserEntity student = new UserEntity();
        student.setId(2L);

        QuizTakeRequestDTO dto = new QuizTakeRequestDTO();
        dto.setQuizId(1L);
        dto.setStudentId(2L);
        dto.setAnswers(Map.of());

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(userRepository.findById(2L)).thenReturn(Optional.of(student));
        when(questionRepository.findAllByQuizId(1L)).thenReturn(List.of());
        when(quizSubmissionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        QuizTakeResponseDTO response = quizTakeService.takeQuiz(dto);

        assertEquals(0, response.getTotalQuestions());
        assertEquals(0, response.getCorrectAnswers());
        assertEquals(0, response.getScore());
        assertFalse(response.isPassed());
    }
}
