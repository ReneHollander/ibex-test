package at.hollander.ibex.repository.impl;

import at.hollander.ibex.entity.DeliverySlot;
import at.hollander.ibex.repository.DeliverySlotRepository;
import at.hollander.ibex.repository.custom.DeliverySlotRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class DeliverySlotRepositoryImpl implements DeliverySlotRepositoryCustom {

    @Autowired
    private DeliverySlotRepository deliverySlotRepository;

    @Override
    public DeliverySlot slotForDay(LocalDate day) {
        return deliverySlotRepository.findById(day.getDayOfWeek().getValue()).orElseThrow(() -> new IllegalStateException("should not happen"));
    }
}
