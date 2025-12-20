package shvalieva.learning_platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
public class SubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private AssignmentEntity assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @Column(name = "submittedAt", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "score")
    private byte score;

    @Column(name = "feedback")
    private String feedback;

    public SubmissionEntity(AssignmentEntity assignment, UserEntity student, LocalDateTime submittedAt, String content, byte score, String feedback) {
        this.assignment = assignment;
        this.student = student;
        this.submittedAt = submittedAt;
        this.content = content;
        this.score = score;
        this.feedback = feedback;
    }
}
