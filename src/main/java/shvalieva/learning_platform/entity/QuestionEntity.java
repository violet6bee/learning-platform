package shvalieva.learning_platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shvalieva.learning_platform.enums.TypeChoiceEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizEntity quiz;

    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    private TypeChoiceEnum typeChoices;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<AnswerOptionEntity> answers = new ArrayList<>();

    public QuestionEntity(QuizEntity quiz, String text, TypeChoiceEnum typeChoices, List<AnswerOptionEntity> answers) {
        this.quiz = quiz;
        this.text = text;
        this.typeChoices = typeChoices;
        this.answers = answers;
    }
}
