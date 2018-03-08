package at.hollander.ibex.repository.helper;

import at.hollander.ibex.View;
import at.hollander.ibex.entity.Product;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
public class ProductAmount {

    @JsonView({View.ProductAmount.Details.class})
    private Product product;

    @JsonView({View.ProductAmount.Details.class})
    private long amount;

    public ProductAmount(int id, String name, long amount) {
        this.product = new Product(id, name, BigDecimal.ZERO);
        this.amount = amount;
    }
}
