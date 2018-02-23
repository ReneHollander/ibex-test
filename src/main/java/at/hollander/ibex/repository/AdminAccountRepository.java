package at.hollander.ibex.repository;

import at.hollander.ibex.entity.AdminAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminAccountRepository extends CrudRepository<AdminAccount, String> {

    public Optional<AdminAccount> findByEmail(String email);

}
