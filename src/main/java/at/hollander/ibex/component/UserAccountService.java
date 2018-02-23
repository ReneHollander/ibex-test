package at.hollander.ibex.component;

import at.hollander.ibex.entity.Account;
import at.hollander.ibex.repository.AccountRepository;
import at.hollander.ibex.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!AuthenticationUtil.isUser(authentication)) {
            throw new IllegalStateException("authentication doesn't have accountDetails authority");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public Account getAccount() {
        return accountRepository.findByEmail(getEmail()).orElseThrow(() -> new IllegalStateException("no account found"));
    }

}
