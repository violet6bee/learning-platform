package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.QuizTakeRequestDTO;
import shvalieva.learning_platform.dto.QuizTakeResponseDTO;
import shvalieva.learning_platform.entity.*;
import shvalieva.learning_platform.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizTakeService {

    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private AnswerOptionRepository answerOptionRepository;
    private QuizSubmissionRepository quizSubmissionRepository;

    @Transactional
    public QuizTakeResponseDTO takeQuiz(QuizTakeRequestDTO dto) {
        QuizEntity quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new EntityNotFoundException("Викторина не найдена"));
        UserEntity student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Студент не найден"));
        List<QuestionEntity> questions =
                questionRepository.findAllByQuizId(quiz.getId());
        int correctCount = 0;
        for (QuestionEntity question : questions) {
            Long selectedOptionId = dto.getAnswers().get(question.getId());
            if (selectedOptionId == null) {
                continue;
            }
            AnswerOptionEntity selectedOption =
                    answerOptionRepository.findById(selectedOptionId)
                            .orElseThrow(() -> new EntityNotFoundException("Вариант ответа не найден"));
            if (selectedOption.isCorrect()) {
                correctCount++;
            }
        }
        int totalQuestions = questions.size();
        int percent = totalQuestions == 0
                ? 0
                : (correctCount * 100) / totalQuestions;
        boolean passed = percent >= 60;
        QuizSubmissionEntity submission = new QuizSubmissionEntity();
        submission.setQuiz(quiz);
        submission.setStudent(student);
        submission.setScore((byte) percent);
        submission.setTakenAt(LocalDateTime.now());
        quizSubmissionRepository.save(submission);
        QuizTakeResponseDTO response = new QuizTakeResponseDTO();
        response.setQuizId(quiz.getId());
        response.setStudentId(student.getId());
        response.setTotalQuestions(totalQuestions);
        response.setCorrectAnswers(correctCount);
        response.setScore(percent);
        response.setPassed(passed);
        return response;
    }
}
