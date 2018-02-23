package at.hollander.ibex.repository;

import at.hollander.ibex.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    public Optional<Account> findByEmail(String email);

}
