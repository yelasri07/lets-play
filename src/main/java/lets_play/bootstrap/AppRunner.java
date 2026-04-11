package lets_play.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lets_play.auth.AuthService;

@Component
public class AppRunner implements ApplicationRunner {

    @Value("${ADMIN_NAME}")
    private String adminName;
    @Value("${ADMIN_EMAIL}")
    private String adminEmail;
    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    private final AuthService authService;

    public AppRunner(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.authService.createAdmin(adminName, adminEmail, adminPassword);
    }
    
}
