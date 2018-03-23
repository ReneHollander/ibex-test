package at.renehollander.util.test;

import at.hollander.ibex.entity.Invoice;
import at.hollander.ibex.entity.Order;
import at.hollander.ibex.entity.OrderItem;
import at.hollander.ibex.entity.Product;
import at.hollander.ibex.entity.id.OrderItemId;
import at.hollander.ibex.repository.helper.ProductAmount;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class InvoiceTest {

    @Test
    public void testProductAmounts() {
        OrderItem o1oi1 = new OrderItem();
        o1oi1.setId(new OrderItemId(1, 1));
        o1oi1.setProductName("Produkt 1");
        o1oi1.setAmount(1);

        OrderItem o1oi2 = new OrderItem();
        o1oi2.setId(new OrderItemId(1, 2));
        o1oi2.setProductName("Produkt 2");
        o1oi2.setAmount(2);

        Order o1 = new Order();
        o1.setItems(ImmutableList.of(o1oi1, o1oi2));

        OrderItem o2oi1 = new OrderItem();
        o2oi1.setId(new OrderItemId(1, 1));
        o2oi1.setProductName("Produkt 1-1");
        o2oi1.setAmount(2);

        OrderItem o2oi2 = new OrderItem();
        o2oi2.setId(new OrderItemId(1, 3));
        o2oi2.setProductName("Produkt 3");
        o2oi2.setAmount(3);

        Order o2 = new Order();
        o2.setItems(ImmutableList.of(o2oi1, o2oi2));

        Invoice invoice = new Invoice();
        invoice.setOrders(ImmutableList.of(o1, o2));

        assertEquals(ImmutableList.builder().add(
                new ProductAmount(new Product(1, "Produkt 1", BigDecimal.ZERO), 3),
                new ProductAmount(new Product(2, "Produkt 2", BigDecimal.ZERO), 2),
                new ProductAmount(new Product(3, "Produkt 3", BigDecimal.ZERO), 3)).build(), invoice.getAmountsTotal());
    }

}
