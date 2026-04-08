package lets_play.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    Optional<User> findByName(String name);
}
