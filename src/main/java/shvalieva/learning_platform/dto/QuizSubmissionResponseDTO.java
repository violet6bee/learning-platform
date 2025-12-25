package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuizSubmissionResponseDTO {
    private Long id;
    private Long quizId;
    private Long studentId;
    private byte score;
    private LocalDateTime takenAt;
}
