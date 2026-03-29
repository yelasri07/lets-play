package lets_play.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;

@Document(collection = "user")
@Builder
@Getter
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
}
