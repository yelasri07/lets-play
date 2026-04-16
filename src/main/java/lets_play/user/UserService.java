package lets_play.user;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lets_play.exception.BadRequestException;
import lets_play.exception.NotFoundException;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return user;
    }

    public List<UserDTO.UserOutput> getUsers() {
        return this.userRepository.findAllUsers();
    }

    public UserDTO.UserOutput updateUser(String userId, UserDTO.UserInput userData) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setName(userData.name());
        user.setEmail(userData.email());

        User updatedUser = this.userRepository.save(user);

        return UserDTO.UserOutput.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole())
                .build();
    }

    public Map<String, String> deleteUser(String userId, User currentUser) {
        if (userId.equals(currentUser.getId())) {
            throw new BadRequestException("Cannot delete yourself");
        }

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        this.userRepository.delete(user);

        return Map.of(
            "userId", user.getId(),
            "message", "User deleted successfully!" 
        );
    }

}
