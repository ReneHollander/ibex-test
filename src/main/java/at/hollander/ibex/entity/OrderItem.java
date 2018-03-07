package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import at.hollander.ibex.entity.id.OrderItemId;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @MapsId("order")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Order order;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private BigDecimal pricePerItem;

    @JsonView(View.Order.Overview.class)
    @Column(nullable = false)
    private int amount;

    public OrderItem(Order order, Product product, int amount) {
        this.id = new OrderItemId(order.getId(), product.getId());
        this.order = order;
        this.productName = product.getName();
        this.amount = amount;
        this.pricePerItem = product.getPrice();
    }

    public OrderItem(Order order, Product product, BigDecimal pricePerItem, int amount) {
        this.id = new OrderItemId(order.getId(), product.getId());
        this.order = order;
        this.productName = product.getName();
        this.pricePerItem = pricePerItem;
        this.amount = amount;
    }

    public int getProductId() {
        return id.getProduct();
    }

    @JsonView(View.Order.Overview.class)
    @JsonGetter("product")
    public Product getProduct() {
        return new Product(id.getProduct(), productName, pricePerItem);
    }

}
