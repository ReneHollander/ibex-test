package at.hollander.ibex.repository;

import at.hollander.ibex.entity.City;
import at.hollander.ibex.entity.id.CityId;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, CityId> {
}
