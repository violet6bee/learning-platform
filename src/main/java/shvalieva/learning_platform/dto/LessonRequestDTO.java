package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonRequestDTO {
    private Long moduleId;
    private String title;
    private String videoUrl;
}
