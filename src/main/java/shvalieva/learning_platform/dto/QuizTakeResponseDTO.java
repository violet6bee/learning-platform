package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizTakeResponseDTO {
    private Long quizId;
    private Long studentId;
    private int totalQuestions;
    private int correctAnswers;
    private int score;
    private boolean passed;
}
