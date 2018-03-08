package at.hollander.ibex.component;

import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.User;
import at.hollander.ibex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    private final UserRepository userRepository;

    @Autowired
    public UserAccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public Account getAccount() {
        return getUser().getAccount();
    }

    public User getUser() {
        return userRepository.findById(getEmail()).orElseThrow(() -> new IllegalStateException("no user found"));
    }

}
