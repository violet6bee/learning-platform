package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerOptionRequestDTO {
    private Long questionId;
    private String text;
    private boolean isCorrect;
}
