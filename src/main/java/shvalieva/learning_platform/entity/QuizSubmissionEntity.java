package shvalieva.learning_platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_submissions")
@Getter
@Setter
@NoArgsConstructor
public class QuizSubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizEntity quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @Column(name = "score", nullable = false)
    private byte score;

    @Column(name = "taken_at", nullable = false)
    private LocalDateTime takenAt;

    public QuizSubmissionEntity(QuizEntity quiz, UserEntity student, byte score, LocalDateTime takenAt) {
        this.quiz = quiz;
        this.student = student;
        this.score = score;
        this.takenAt = takenAt;
    }
}
