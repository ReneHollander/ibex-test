package at.hollander.ibex.entity;

import at.hollander.ibex.entity.id.RecurringOrderItemId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RecurringOrderItem {

    @EmbeddedId
    @JsonIgnore
    private RecurringOrderItemId recurringOrderItemId;

    @MapsId("recurringOrderId")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonIgnore
    private RecurringOrder recurringOrder;

    @MapsId("product")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Product product;

    private int amount;

    public RecurringOrderItem(RecurringOrder recurringOrder, Product product, int amount) {
        this.recurringOrderItemId = new RecurringOrderItemId(recurringOrder.getRecurringOrderId(), product.getId());
        this.recurringOrder = recurringOrder;
        this.product = product;
        this.amount = amount;
    }
}
