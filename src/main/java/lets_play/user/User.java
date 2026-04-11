package lets_play.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "user")
@Builder
@Getter
@Setter
public class User implements UserDetails {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role));

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.name;
    }
}
