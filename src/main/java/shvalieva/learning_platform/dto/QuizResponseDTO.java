package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResponseDTO {
    private Long id;
    private Long courseId;
    private String title;
}
