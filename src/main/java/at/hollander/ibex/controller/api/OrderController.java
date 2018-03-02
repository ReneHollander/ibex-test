package at.hollander.ibex.controller.api;

import at.hollander.ibex.View;
import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.Order;
import at.hollander.ibex.repository.OrderRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserAccountService userAccountService;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserAccountService userAccountService) {
        this.orderRepository = orderRepository;
        this.userAccountService = userAccountService;
    }

    @JsonView(View.Order.List.class)
    @RequestMapping(value = "/orders/pending", method = {RequestMethod.GET})
    public Iterable<Order> ordersPending() {
        Account account = userAccountService.getAccount();
        return orderRepository.findAllByAccountAndInvoiceIsNull(account);
    }

    @JsonView(View.OrderOverviewAndProductOverview.class)
    @RequestMapping(value = "/order/{id}", method = {RequestMethod.GET})
    public Order order(@PathVariable("id") int id) {
        Account account = userAccountService.getAccount();
        // TODO: set appropriate http status
        return orderRepository.findByIdAndAccount(id, account).orElseThrow(() -> new IllegalArgumentException("order doesn't exist for this user"));
    }

}
