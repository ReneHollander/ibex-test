package at.hollander.ibex.controller.api;

import at.hollander.ibex.entity.NewsletterRegistration;
import at.hollander.ibex.repository.CityRepository;
import at.hollander.ibex.repository.NewsletterRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class NewsletterController {

    private final CityRepository cityRepository;
    private final NewsletterRegistrationRepository newsletterRegistrationRepository;

    @Autowired
    public NewsletterController(CityRepository cityRepository, NewsletterRegistrationRepository newsletterRegistrationRepository) {
        this.cityRepository = cityRepository;
        this.newsletterRegistrationRepository = newsletterRegistrationRepository;
    }

    @RequestMapping(value = "/newsletter/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody NewsletterRegistration request) {
        if (!cityRepository.existsCityByCityId(request.getCity().getCityId())) {
            throw new IllegalArgumentException("city does not exist");
        }
        if (newsletterRegistrationRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("email already subscribed");
        }
        newsletterRegistrationRepository.save(request);
    }

}
