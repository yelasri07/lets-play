package lets_play.auth;

public class AuthDTO {

    public static record RegisterInput(
            String name,
            String email,
            String password) {
    }

}
