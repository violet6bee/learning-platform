package shvalieva.learning_platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private ModuleEntity module;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "videoUrl", nullable = false)
    private String videoUrl;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private List<AssignmentEntity> assignments = new ArrayList<>();

    public LessonEntity(ModuleEntity module, String title, String videoUrl) {
        this.module = module;
        this.title = title;
        this.videoUrl = videoUrl;
    }
}
