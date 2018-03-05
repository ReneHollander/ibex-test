package at.hollander.ibex.controller.api;

import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.RecurringOrder;
import at.hollander.ibex.entity.RecurringOrderItem;
import at.hollander.ibex.repository.ProductRepository;
import at.hollander.ibex.repository.RecurringOrderItemRepository;
import at.hollander.ibex.repository.RecurringOrderRepository;
import at.hollander.ibex.util.CollectionUtil;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecurringOrderController {

    private final UserAccountService userAccountService;
    private final ProductRepository productRepository;
    private final RecurringOrderRepository recurringOrderRepository;
    private final RecurringOrderItemRepository recurringOrderItemRepository;

    @Autowired
    public RecurringOrderController(UserAccountService userAccountService, ProductRepository productRepository, RecurringOrderRepository recurringOrderRepository, RecurringOrderItemRepository recurringOrderItemRepository) {
        this.userAccountService = userAccountService;
        this.productRepository = productRepository;
        this.recurringOrderRepository = recurringOrderRepository;
        this.recurringOrderItemRepository = recurringOrderItemRepository;
    }

    @RequestMapping(value = "/recurringorders", method = {RequestMethod.GET})
    public List<RecurringOrder> recurringOrders() {
        Account account = userAccountService.getAccount();
        return account.getRecurringOrders();
    }

    @RequestMapping(value = "/recurringorder", method = RequestMethod.POST)
    public RecurringOrder recurringOrderSave(@RequestBody RecurringOrder order) {
        Account account = userAccountService.getAccount();

        RecurringOrder dbOrder = recurringOrderRepository.findByAccountAndDeliverySlot(account, order.getDeliverySlot()).orElseThrow(() -> new IllegalStateException("user has no recurring order with slot " + order.getDeliverySlot()));

        List<RecurringOrderItem> toDelete = new ArrayList<>();
        List<Tuple2<RecurringOrderItem, RecurringOrderItem>> toUpdate = new ArrayList<>();
        List<RecurringOrderItem> toInsert = new ArrayList<>();
        CollectionUtil.splitBy(dbOrder.getItems(), order.getItems(), RecurringOrderItem.COMPARE_BY_PRODUCT, toDelete, toUpdate, toInsert);

        for (RecurringOrderItem item : toInsert) {
            dbOrder.getItems().add(new RecurringOrderItem(
                    dbOrder,
                    productRepository.findById(item.getProduct().getId()).orElseThrow(() -> new IllegalArgumentException("product does not exist " + item.getProduct().getId())),
                    item.getAmount()
            ));
        }

        for (Tuple2<RecurringOrderItem, RecurringOrderItem> item : toUpdate) {
            item.v1.setAmount(item.v2.getAmount());
        }

        CollectionUtil.removeAllBy(dbOrder.getItems(), toDelete, RecurringOrderItem.COMPARE_BY_PRODUCT);

        recurringOrderItemRepository.deleteAll(toDelete);

        dbOrder.setEnabled(order.isEnabled());
        dbOrder = recurringOrderRepository.save(dbOrder);
        return dbOrder;
    }

}