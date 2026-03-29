package lets_play.auth;

import org.springframework.stereotype.Service;

import lets_play.user.User;
import lets_play.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(AuthDTO.RegisterInput userData) {
        User user = User.builder()
                .name(userData.name())
                .email(userData.email())
                .password(userData.password())
                .role("USER")
                .build();

        this.userRepository.insert(user);
    }

}
