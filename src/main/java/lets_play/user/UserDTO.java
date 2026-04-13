package lets_play.user;

public class UserDTO {
    
    public static record UserOutput (
        String id,
        String name,
        String email,
        String role
    ) {}

}
