package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerOptionRequestDTO {
    private String text;
    private boolean isCorrect;
}
