package shvalieva.learning_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String firstName;
    private String secondName;
    private String email;
    private String password;
}
