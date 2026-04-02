package lets_play.auth;

import org.springframework.stereotype.Service;

import lets_play.exception.ConflictException;
import lets_play.user.User;
import lets_play.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthDTO.RegisterOutput createUser(AuthDTO.RegisterInput userData) {
        if (this.userRepository.existsByName(userData.name())) {
            throw new ConflictException("The user name already exists, please choose another name.");
        }

        if (this.userRepository.existsByEmail(userData.email())) {
            throw new ConflictException("The user email already exists, please choose another name.");
        }

        User user = User.builder()
                .name(userData.name())
                .email(userData.email())
                .password(userData.password())
                .role("USER")
                .build();

        User newUser = this.userRepository.insert(user);
        String token = this.jwtService.generateToken(newUser);

        return AuthDTO.RegisterOutput.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .token(token)
                .build();
    }

}
