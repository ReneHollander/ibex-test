package at.hollander.ibex.controller.api;

import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.RecurringOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecurringOrderController {

    private final UserAccountService userAccountService;

    @Autowired
    public RecurringOrderController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping(value = "/recurringorders", method = {RequestMethod.GET, RequestMethod.POST})
    public List<RecurringOrder> recurringOrders() {
        Account account = userAccountService.getAccount();
        return account.getRecurringOrders();
    }

}
