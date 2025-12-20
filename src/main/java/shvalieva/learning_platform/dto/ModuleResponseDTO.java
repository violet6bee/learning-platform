package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleResponseDTO {
    private Long id;
    private Long courseId;
    private String title;
    private int orderIndex;
    private String description;
}
