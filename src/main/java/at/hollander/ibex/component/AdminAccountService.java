package at.hollander.ibex.component;

import at.hollander.ibex.entity.AdminAccount;
import at.hollander.ibex.repository.AdminAccountRepository;
import at.hollander.ibex.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AdminAccountService {

    private final AdminAccountRepository adminAccountRepository;

    @Autowired
    public AdminAccountService(AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!AuthenticationUtil.isAdmin(authentication)) {
            throw new IllegalStateException("authentication doesn't have accountDetails authority");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public AdminAccount getAccount() {
        return adminAccountRepository.findByEmail(getEmail()).orElseThrow(() -> new IllegalStateException("no account found"));
    }

}
