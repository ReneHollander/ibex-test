package at.hollander.ibex.controller.api;

import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.Account;
import lombok.Builder;
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

    @RequestMapping(value = "/initial", method = {RequestMethod.GET, RequestMethod.POST})
    public LoginResponse initial() {
        Account account = userAccountService.getAccount();
        return LoginResponse.builder().email(account.getEmail()).name(account.getName()).build();
    }

    @Data
    @Builder
    public static class LoginResponse {
        private String email;
        private String name;
        @Builder.Default
        private String role = "USER";
    }

}
