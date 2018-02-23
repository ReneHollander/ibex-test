package at.hollander.ibex.controller.api.admin;

import at.hollander.ibex.component.AdminAccountService;
import at.hollander.ibex.entity.AdminAccount;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminInitialController {

    private final AdminAccountService adminAccountService;

    @Autowired
    public AdminInitialController(AdminAccountService adminAccountService) {
        this.adminAccountService = adminAccountService;
    }

    @RequestMapping(value = "/initial", method = {RequestMethod.GET, RequestMethod.POST})
    public LoginResponse initial() {
        AdminAccount account = adminAccountService.getAccount();
        return LoginResponse.builder().email(account.getEmail()).name(account.getName()).build();
    }

    @Data
    @Builder
    public static class LoginResponse {
        private String email;
        private String name;
        @Builder.Default
        private String role = "ADMIN";
    }

}
