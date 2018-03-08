package at.hollander.ibex.component;

import at.hollander.ibex.entity.User;
import at.hollander.ibex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class IbexUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public IbexUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException("user with email " + email + " not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthoritiesForRole(user.getRole()));

    }

    private List<GrantedAuthority> getAuthoritiesForRole(User.Role role) {
        switch (role) {
            case USER:
                return AuthorityUtils.createAuthorityList("USER");
            case ADMIN:
                return AuthorityUtils.createAuthorityList("ADMIN");
            default:
                throw new IllegalStateException("role " + role + " has no authority mapping");
        }
    }
}
