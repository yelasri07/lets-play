package lets_play.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lets_play.exception.BadRequestException;
import lets_play.exception.ConflictException;
import lets_play.user.User;
import lets_play.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
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
                .password(this.passwordEncoder.encode(userData.password()))
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

    public AuthDTO.LoginOutput authenticate(AuthDTO.LoginInput userData) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userData.name(),
                            userData.password()));

            var user = (User) authentication.getPrincipal();
            var jwt = jwtService.generateToken(user);

            return AuthDTO.LoginOutput.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .token(jwt)
                    .build();
        } catch (AuthenticationException e) {
            throw new BadRequestException("User name or password is incorrect");
        }
    }

    public void createAdmin(String name, String email, String password) {
        if (this.userRepository.existsByName(name)) {
            return;
        }

        User admin = User.builder()
                .name(name)
                .email(email)
                .password(this.passwordEncoder.encode(password))
                .role("ADMIN")
                .build();

        this.userRepository.insert(admin);
    }

}
