package at.hollander.ibex.repository;

import at.hollander.ibex.entity.OrderItem;
import at.hollander.ibex.entity.id.OrderItemId;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, OrderItemId> {
}
