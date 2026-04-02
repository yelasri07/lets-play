package lets_play.auth;

import lombok.Builder;

public class AuthDTO {

    public static record RegisterInput(
            String name,
            String email,
            String password) {
    }

    @Builder
    public static record RegisterOutput(
            String id,
            String name,
            String email,
            String token) {

    }

}
