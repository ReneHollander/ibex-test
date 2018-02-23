package at.hollander.ibex.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class RegisterController {
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpSession session) {
        session.invalidate();
    }


}
