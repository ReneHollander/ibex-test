package at.hollander.ibex.controller.api;

import at.hollander.ibex.View;
import at.hollander.ibex.entity.City;
import at.hollander.ibex.repository.CityRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityRepository cityRepository;

    @Autowired
    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @RequestMapping("/cities/enabled")
    @JsonView({View.Endpoint.EnabledCities.class})
    public Iterable<City> enabledCities() {
        return cityRepository.findAllByEnabledIsTrue();
    }

    @RequestMapping("/cities/disabled")
    @JsonView({View.Endpoint.DisabledCities.class})
    public Iterable<City> disabledCities() {
        return cityRepository.findAllByEnabledIsFalse();
    }

}
