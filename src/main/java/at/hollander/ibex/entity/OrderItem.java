package at.hollander.ibex.entity;

import at.hollander.ibex.entity.id.OrderItemId;
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
    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    @MapsId("product")
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private BigDecimal pricePerItem;

}
