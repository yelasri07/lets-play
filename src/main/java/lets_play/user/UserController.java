package lets_play.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String getAll(@AuthenticationPrincipal UserDetails user) {
        return "Hello world!";
    }

}
