package at.hollander.ibex.controller.api;

import at.hollander.ibex.View;
import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.Account;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InitialController {

    private final UserAccountService userAccountService;

    @Autowired
    public InitialController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @JsonView({View.Endpoint.Initial.class})
    @RequestMapping(value = "/initial", method = {RequestMethod.GET, RequestMethod.POST})
    public LoginResponse initial() {
        return new LoginResponse(userAccountService.getAccount(), "USER");
    }

    @AllArgsConstructor
    public static class LoginResponse {
        @JsonView(View.Endpoint.Initial.class)
        private Account account;
        @JsonView(View.Endpoint.Initial.class)
        private String role;
    }

}
