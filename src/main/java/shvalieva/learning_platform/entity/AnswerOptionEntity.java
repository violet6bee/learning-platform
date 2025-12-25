package shvalieva.learning_platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "answer_options")
@Getter
@Setter
@NoArgsConstructor
public class AnswerOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "is_correct", nullable = false)
    private boolean correct;

    public AnswerOptionEntity(QuestionEntity question, String text, boolean correct) {
        this.question = question;
        this.text = text;
        this.correct = correct;
    }
}
