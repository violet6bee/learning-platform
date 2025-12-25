package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubmissionResponseDTO {
    private Long id;
    private Long assignmentId;
    private Long studentId;
    private LocalDateTime submittedAt;
    private String content;
    private byte score;
    private String feedback;
}
