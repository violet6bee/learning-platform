package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubmissionRequestDTO {
    private Long assignmentId;
    private Long studentId;
    private LocalDateTime submittedAt;
    private String content;
    private byte score;
    private String feedback;
}
