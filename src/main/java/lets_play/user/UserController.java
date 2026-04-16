package lets_play.user;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO.UserOutput> getAll(@AuthenticationPrincipal UserDetails user) {
        return this.userService.getUsers();
    }

    @PutMapping("/{id}")
    public UserDTO.UserOutput update(@PathVariable("id") String userId,
            @Valid @RequestBody UserDTO.UserInput userData) {
        return this.userService.updateUser(userId, userData);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable("id") String userId) {
        return this.userService.deleteUser(userId);
    }

}
