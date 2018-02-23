package at.hollander.ibex.controller.api;

import at.hollander.ibex.entity.Product;
import at.hollander.ibex.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/products", method = {RequestMethod.GET})
    public Iterable<Product> products() {
        return productRepository.findAll();
    }

    @RequestMapping(value = "/products/count", method = {RequestMethod.GET})
    public long productCount() {
        return productRepository.count();
    }

}
