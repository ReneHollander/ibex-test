package at.hollander.ibex.component;

import at.hollander.ibex.entity.*;
import at.hollander.ibex.repository.DeliverySlotRepository;
import at.hollander.ibex.repository.OrderRepository;
import at.hollander.ibex.repository.RecurringOrderRepository;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final DeliverySlotRepository deliverySlotRepository;
    private final RecurringOrderRepository recurringOrderRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(DeliverySlotRepository deliverySlotRepository, RecurringOrderRepository recurringOrderRepository, OrderRepository orderRepository) {
        this.deliverySlotRepository = deliverySlotRepository;
        this.recurringOrderRepository = recurringOrderRepository;
        this.orderRepository = orderRepository;
    }

    public List<Order> createOrdersFromRecurringOrders(LocalDate day) {
        DeliverySlot slot = deliverySlotRepository.slotForDay(day);
        List<Order> orders = recurringOrderRepository
                .findAllByDeliverySlotAndEnabledIsTrue(slot).stream()
                .map(recurringOrder -> orderFromRecurringOrder(recurringOrder, day))
                .collect(Collectors.toList());
        List<Order> saved = Lists.newLinkedList(orderRepository.saveAll(orders));
        orders = new ArrayList<>();
        Iterables.addAll(orders, saved);
        return orders;
    }

    private Order orderFromRecurringOrder(RecurringOrder recurringOrder, LocalDate day) {
        Order order = new Order();
        order.setAccount(recurringOrder.getAccount());
        order.setDeliveryTime(day.atTime(recurringOrder.getDeliverySlot().getDeliverBy()));
        order.setOrderTime(LocalDateTime.now());
        order.setAddress(recurringOrder.getAccount().getAddress());
        order.setCity(recurringOrder.getAccount().getCityName());
        order.setPostcode(recurringOrder.getAccount().getCityPostcode());
        order.setDeliveryNote(recurringOrder.getAccount().getDeliveryNote());
        order.setPriceShipping(recurringOrder.getAccount().getCity().getPriceShipping());
        order.setItems(recurringOrder
                .getItems().stream()
                .map(rItem -> new OrderItem(order, rItem.getProduct(), rItem.getAmount()))
                .collect(Collectors.toList()));
        return order;
    }

    public Map<Product, Integer> addProducts(List<Order> orders) {
        return orders.stream()
                .flatMap((order) -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProduct, Collectors.summingInt(OrderItem::getAmount)));
    }
}
