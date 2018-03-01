package at.hollander.ibex.repository;

import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {

    public List<Invoice> findAllByAccount(Account account);

    public long countByAccount(Account account);

    public Optional<Invoice> findByIdAndAccount(int id, Account account);


}
