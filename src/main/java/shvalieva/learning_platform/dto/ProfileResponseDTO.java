package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDTO {
    private Long id;
    private String biography;
    private String phone;
    private String avatarUrl;
}
