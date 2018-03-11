package at.hollander.ibex.repository;

import at.hollander.ibex.entity.NewsletterRegistration;
import org.springframework.data.repository.CrudRepository;

public interface NewsletterRegistrationRepository extends CrudRepository<NewsletterRegistration, String> {

    public boolean existsByEmail(String email);

}
