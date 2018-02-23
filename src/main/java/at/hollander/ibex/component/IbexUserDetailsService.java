package at.hollander.ibex.component;

import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.AdminAccount;
import at.hollander.ibex.repository.AccountRepository;
import at.hollander.ibex.repository.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
public class IbexUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AdminAccountRepository adminAccountRepository;

    @Autowired
    public IbexUserDetailsService(AccountRepository accountRepository, AdminAccountRepository adminAccountRepository) {
        this.accountRepository = accountRepository;
        this.adminAccountRepository = adminAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        Optional<AdminAccount> optionalAdminAccount = adminAccountRepository.findById(email);
        if (optionalAccount.isPresent() && optionalAdminAccount.isPresent()) {
            throw new IllegalStateException("accountDetails and admin exist for the same email '" + email + "'");
        }
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            return new User(account.getEmail(), account.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
        }
        if (optionalAdminAccount.isPresent()) {
            AdminAccount adminAccount = optionalAdminAccount.get();
            return new User(adminAccount.getEmail(), adminAccount.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("ADMIN"));
        }
        throw new UsernameNotFoundException("could not find the accountDetails '" + email + "'");
    }
}
