package at.hollander.ibex.repository.helper;

import at.hollander.ibex.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.math.BigDecimal;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DeliveryFeeAmount {

    @JsonView({View.DeliveryFeeAmount.Details.class})
    private BigDecimal priceShipping;

    @JsonView({View.DeliveryFeeAmount.Details.class})
    private long amount;

}
