package at.hollander.ibex.entity;

import at.hollander.ibex.entity.id.RecurringOrderId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"items", "account"})
@ToString(exclude = {"items", "account"})
public class RecurringOrder {

    @EmbeddedId
    @JsonIgnore
    private RecurringOrderId recurringOrderId;

    @MapsId("account")
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;

    @MapsId("deliverySlot")
    @ManyToOne(cascade = CascadeType.ALL)
    private DeliverySlot deliverySlot;

    private boolean enabled;

    @OneToMany(mappedBy = "recurringOrder", cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<RecurringOrderItem> items;

    public RecurringOrder(Account account, DeliverySlot deliverySlot, boolean enabled) {
        this.recurringOrderId = new RecurringOrderId(account.getId(), deliverySlot.getId());
        this.account = account;
        this.deliverySlot = deliverySlot;
        this.enabled = enabled;
    }

}
