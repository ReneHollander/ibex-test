package at.hollander.ibex.repository;

import at.hollander.ibex.entity.RecurringOrder;
import at.hollander.ibex.entity.id.RecurringOrderId;
import org.springframework.data.repository.CrudRepository;

public interface RecurringOrderRepository extends CrudRepository<RecurringOrder, RecurringOrderId> {
}
