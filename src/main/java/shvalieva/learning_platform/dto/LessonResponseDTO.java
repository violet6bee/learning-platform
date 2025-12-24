package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonResponseDTO {
    private Long id;
    private Long moduleId;
    private String title;
    private String videoUrl;
}
