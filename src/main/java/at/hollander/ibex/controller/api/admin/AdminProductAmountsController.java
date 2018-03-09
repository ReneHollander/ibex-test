package at.hollander.ibex.controller.api.admin;

import at.hollander.ibex.View;
import at.hollander.ibex.repository.OrderRepository;
import at.hollander.ibex.repository.helper.ProductAmount;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminProductAmountsController {

    private final OrderRepository orderRepository;

    @Autowired
    public AdminProductAmountsController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @JsonView({View.Endpoint.Admin.ProductAmounts.class})
    @RequestMapping("/productamounts/{date}")
    public List<ProductAmount> productAmount(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return orderRepository.addProducts(date);
    }

}
