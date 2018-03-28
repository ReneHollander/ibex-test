package at.renehollander.ibex.entity.test;

import at.hollander.ibex.entity.Invoice;
import at.hollander.ibex.entity.Order;
import at.hollander.ibex.entity.OrderItem;
import at.hollander.ibex.entity.Product;
import at.hollander.ibex.entity.id.OrderItemId;
import at.hollander.ibex.repository.helper.DeliveryFeeAmount;
import at.hollander.ibex.repository.helper.ProductAmount;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class InvoiceTest {

    @Test
    public void testProductAmounts() {
        OrderItem o1oi1 = new OrderItem();
        o1oi1.setId(new OrderItemId(1, 1));
        o1oi1.setProductName("Produkt 1");
        o1oi1.setPricePerItem(BigDecimal.ONE);
        o1oi1.setAmount(1);

        OrderItem o1oi2 = new OrderItem();
        o1oi2.setId(new OrderItemId(1, 2));
        o1oi2.setProductName("Produkt 2");
        o1oi2.setPricePerItem(BigDecimal.ONE);
        o1oi2.setAmount(2);

        OrderItem o1oi3 = new OrderItem();
        o1oi3.setId(new OrderItemId(1, 3));
        o1oi3.setProductName("Produkt 3");
        o1oi3.setPricePerItem(BigDecimal.ONE);
        o1oi3.setAmount(2);

        Order o1 = new Order();
        o1.setId(1);
        o1.setItems(ImmutableList.of(o1oi1, o1oi2, o1oi3));

        OrderItem o2oi1 = new OrderItem();
        o2oi1.setId(new OrderItemId(2, 1));
        o2oi1.setProductName("Produkt 1-1");
        o2oi1.setPricePerItem(BigDecimal.ONE);
        o2oi1.setAmount(2);

        OrderItem o2oi2 = new OrderItem();
        o2oi2.setId(new OrderItemId(2, 3));
        o2oi2.setProductName("Produkt 3");
        o2oi2.setPricePerItem(BigDecimal.ONE);
        o2oi2.setAmount(3);

        OrderItem o2oi3 = new OrderItem();
        o2oi3.setId(new OrderItemId(2, 2));
        o2oi3.setProductName("Produkt 2");
        o2oi3.setPricePerItem(BigDecimal.TEN);
        o2oi3.setAmount(5);

        Order o2 = new Order();
        o2.setId(2);
        o2.setItems(ImmutableList.of(o2oi1, o2oi2, o2oi3));

        Invoice invoice = new Invoice();
        invoice.setOrders(ImmutableList.of(o1, o2));

        assertThat(invoice.getProductAmounts(), containsInAnyOrder(
                new ProductAmount(new Product(1, "Produkt 1", BigDecimal.ONE), 1),
                new ProductAmount(new Product(2, "Produkt 2", BigDecimal.ONE), 2),
                new ProductAmount(new Product(3, "Produkt 3", BigDecimal.ONE), 5),
                new ProductAmount(new Product(2, "Produkt 2", BigDecimal.TEN), 5),
                new ProductAmount(new Product(1, "Produkt 1-1", BigDecimal.ONE), 2)));
    }

    @Test
    public void testDeliveryFees() {
        Order o1 = new Order();
        o1.setId(1);
        o1.setPriceShipping(BigDecimal.ONE);

        Order o2 = new Order();
        o2.setId(2);
        o2.setPriceShipping(BigDecimal.TEN);

        Order o3 = new Order();
        o3.setId(3);
        o3.setPriceShipping(BigDecimal.ONE);

        Order o4 = new Order();
        o4.setId(4);
        o4.setPriceShipping(BigDecimal.TEN);

        Order o5 = new Order();
        o5.setId(5);
        o5.setPriceShipping(BigDecimal.ZERO);

        Order o6 = new Order();
        o6.setId(6);
        o6.setPriceShipping(new BigDecimal("13.37"));

        Invoice invoice = new Invoice();
        invoice.setOrders(ImmutableList.of(o1, o2, o3, o4, o5, o6));

        assertThat(invoice.getDeliveryFeeAmounts(), containsInAnyOrder(
                new DeliveryFeeAmount(BigDecimal.ONE, 2),
                new DeliveryFeeAmount(BigDecimal.TEN, 2),
                new DeliveryFeeAmount(BigDecimal.ZERO, 1),
                new DeliveryFeeAmount(new BigDecimal("13.37"), 1)));
    }

}