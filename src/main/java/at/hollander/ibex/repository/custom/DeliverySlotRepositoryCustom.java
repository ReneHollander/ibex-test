package at.hollander.ibex.repository.custom;

import at.hollander.ibex.entity.DeliverySlot;

import java.time.LocalDate;

public interface DeliverySlotRepositoryCustom {

    DeliverySlot slotForDay(LocalDate day);

}
