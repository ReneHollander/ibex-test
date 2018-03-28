package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Product {

    @Id
    @JsonView({View.Product.Overview.class, View.Product.Details.class})
    private int id;

    @Column(nullable = false)
    @JsonView({View.Product.Overview.class, View.Product.Details.class})
    private String name;

    @Column(nullable = false)
    @JsonView(View.Product.Details.class)
    private BigDecimal price;

}
