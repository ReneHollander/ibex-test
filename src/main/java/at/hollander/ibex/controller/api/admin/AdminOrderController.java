package at.hollander.ibex.controller.api.admin;

import at.hollander.ibex.View;
import at.hollander.ibex.component.OrderService;
import at.hollander.ibex.entity.Order;
import at.hollander.ibex.repository.OrderRepository;
import at.hollander.ibex.view.pdf.impl.OrderSummaryPDF;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @JsonView({View.Endpoint.Admin.OrderSummary.class})
    @RequestMapping("/ordersummary/{date}")
    public List<Order> orderSummary(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return orderRepository.findAllByDeliveryTimeDate(date);
    }

    @RequestMapping(value = "/ordersummary/{date}/create", method = RequestMethod.POST)
    public void orderCreate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        orderService.createOrdersFromRecurringOrders(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @RequestMapping("/ordersummary/{date}/pdf")
    public ModelAndView orderSummaryPdf(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return OrderSummaryPDF.create(orderRepository.findAllByDeliveryTimeDate(date));
    }

}

