package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerOptionResponseDTO {
    private Long id;
    private String text;
    private boolean isCorrect;
}
