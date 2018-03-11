package at.hollander.ibex.controller.api;

import at.hollander.ibex.View;
import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public AccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping("/account/details")
    @JsonView({View.Endpoint.Account.Details.class})
    public User details() {
        return userAccountService.getUser();
    }

}
