package at.hollander.ibex.repository;

import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    public Iterable<Order> findAllByAccountAndInvoiceIsNull(Account account);

    public Optional<Order> findByIdAndAccount(int id, Account account);
}
