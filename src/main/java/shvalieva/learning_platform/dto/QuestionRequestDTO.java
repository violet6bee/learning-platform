package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;
import shvalieva.learning_platform.enums.TypeChoiceEnum;

@Getter
@Setter
public class QuestionRequestDTO {
    private Long quizId;
    private String text;
    private TypeChoiceEnum typeChoices;
    private boolean correct;
}
