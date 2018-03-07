package at.hollander.ibex.repository.helper;

import at.hollander.ibex.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
public class ProductOrderSummary {
    private Product product;
    private long amount;

    public ProductOrderSummary(int id, String name, long amount) {
        this.product = new Product(id, name, BigDecimal.ZERO);
        this.amount = amount;
    }
}
