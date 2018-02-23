package at.hollander.ibex.repository;

import at.hollander.ibex.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
