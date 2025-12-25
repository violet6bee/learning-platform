package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QuizTakeRequestDTO {
    private Long studentId;
    private Long quizId;
    private Map<Long, Long> answers;
}
