package at.hollander.ibex.repository.helper;

import at.hollander.ibex.View;
import at.hollander.ibex.entity.Product;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class ProductAmount {

    @JsonView({View.ProductAmount.Details.class})
    private Product product;

    @JsonView({View.ProductAmount.Details.class})
    private long amount;

    public ProductAmount(int id, String name, long amount) {
        this(new Product(id, name, BigDecimal.ZERO), amount);
    }

    public ProductAmount(Product product, long amount) {
        this.product = product;
        this.amount = amount;
    }
}
