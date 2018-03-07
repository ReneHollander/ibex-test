package at.hollander.ibex.repository;

import at.hollander.ibex.entity.DeliverySlot;
import at.hollander.ibex.repository.custom.DeliverySlotRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

public interface DeliverySlotRepository extends CrudRepository<DeliverySlot, Integer>, DeliverySlotRepositoryCustom {
}
