package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CourseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private Long categoryId;
    private Set<TagResponseDTO> tags;
}
