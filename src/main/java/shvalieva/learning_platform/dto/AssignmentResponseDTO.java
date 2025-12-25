package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AssignmentResponseDTO {
    private Long id;
    private Long lessonId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private byte maxScore;
}
