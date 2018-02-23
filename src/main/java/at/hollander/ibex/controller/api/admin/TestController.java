package at.hollander.ibex.controller.api.admin;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class TestController {

    @RequestMapping("/test")
    public Map<Object, Object> test() {
        return ImmutableMap.builder().
                put("msg", "Hello Admin").
                put("id", 1).
                build();
    }

}
