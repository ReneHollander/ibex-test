package at.hollander.ibex.repository;

import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.DeliverySlot;
import at.hollander.ibex.entity.RecurringOrder;
import at.hollander.ibex.entity.id.RecurringOrderId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecurringOrderRepository extends CrudRepository<RecurringOrder, RecurringOrderId> {

    Optional<RecurringOrder> findByAccountAndDeliverySlot(Account account, DeliverySlot deliverySlot);

}
