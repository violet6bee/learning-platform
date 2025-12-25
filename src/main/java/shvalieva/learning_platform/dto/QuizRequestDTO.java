package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizRequestDTO {
    private Long courseId;
    private String title;
}
