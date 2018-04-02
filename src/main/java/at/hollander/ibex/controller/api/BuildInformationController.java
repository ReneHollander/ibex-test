package at.hollander.ibex.controller.api;

import at.hollander.ibex.pojo.BuildInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BuildInformationController {

    private final BuildInformation buildInformation;

    @Autowired
    public BuildInformationController(BuildInformation buildInformation) {
        this.buildInformation = buildInformation;
    }

    @RequestMapping("/build")
    public BuildInformation buildInformation() {
        return buildInformation;
    }

}
