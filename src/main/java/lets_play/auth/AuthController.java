package lets_play.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthDTO.RegisterOutput register(@Valid @RequestBody AuthDTO.RegisterInput userData) {
        return this.authService.createUser(userData);
    }

    @PostMapping("/login")
    public AuthDTO.LoginOutput login(@RequestBody AuthDTO.LoginInput userData) {
        return this.authService.authenticate(userData);
    }
    

}
