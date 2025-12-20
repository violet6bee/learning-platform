package shvalieva.learning_platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
@Getter
@Setter
@NoArgsConstructor
public class ModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "orderIndex", nullable = false)
    private int orderIndex;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LessonEntity> lessons = new ArrayList<>();

    public ModuleEntity(CourseEntity course, String title, int orderIndex, String description) {
        this.course = course;
        this.title = title;
        this.orderIndex = orderIndex;
        this.description = description;
    }
}
