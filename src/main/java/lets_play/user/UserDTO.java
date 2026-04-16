package lets_play.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class UserDTO {
    
    public static record UserInput (
        @NotBlank(message = "User name cannot be empty")
        @Size(min = 3, max = 20, message = "User name must be between 3 and 20 character")
        String name,
        @NotBlank(message = "User email cannot be empty")
        @Email(message = "Please provide a valid email")
        String email
    ) {
        public UserInput {
            if (name != null && email != null) {
                name = name.trim();
                email = email.trim();
            }
        }
    }

    @Builder
    public static record UserOutput (
        String id,
        String name,
        String email,
        String role
    ) {}

}
