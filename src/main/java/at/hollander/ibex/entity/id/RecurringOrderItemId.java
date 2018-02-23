package at.hollander.ibex.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RecurringOrderItemId implements Serializable {

    private RecurringOrderId recurringOrderId;
    private int product;

}