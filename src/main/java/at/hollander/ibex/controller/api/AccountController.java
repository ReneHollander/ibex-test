package at.hollander.ibex.controller.api;

import at.hollander.ibex.View;
import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.User;
import at.hollander.ibex.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserAccountService userAccountService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(UserAccountService userAccountService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userAccountService = userAccountService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/account/details")
    @JsonView({View.Endpoint.Account.Details.class})
    public User details() {
        return userAccountService.getUser();
    }

    @RequestMapping(value = "/account/password", method = RequestMethod.POST)
    public void changePassword(@Valid @RequestBody ChangePasswordRequest req) {
        User user = userAccountService.getUser();
        if (passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("invalid password");
        }
    }

    @Data
    private static class ChangePasswordRequest {
        @NotEmpty
        private String oldPassword;
        @NotEmpty
        @Size(min = 8)
        private String newPassword;
    }

}
