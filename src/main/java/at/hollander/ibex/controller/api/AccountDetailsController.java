package at.hollander.ibex.controller.api;

import at.hollander.ibex.View;
import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.Account;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountDetailsController {

    private final UserAccountService userAccountService;

    @Autowired
    public AccountDetailsController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping("/accountdetails")
    @JsonView({View.Endpoint.AccountDetails.class})
    public Account account() {
        return userAccountService.getAccount();
    }


}
