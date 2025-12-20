package shvalieva.learning_platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<QuestionEntity> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<QuizSubmissionEntity> quizSubmissions = new ArrayList<>();

    public QuizEntity(CourseEntity course, String title, List<QuestionEntity> questions) {
        this.course = course;
        this.title = title;
        this.questions = questions;
    }
}
