package at.hollander.ibex.repository;

import at.hollander.ibex.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
