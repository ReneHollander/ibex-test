package at.hollander.ibex.repository;

import at.hollander.ibex.entity.RecurringOrderItem;
import at.hollander.ibex.entity.id.RecurringOrderItemId;
import org.springframework.data.repository.CrudRepository;

public interface RecurringOrderItemRepository extends CrudRepository<RecurringOrderItem, RecurringOrderItemId> {
}
