package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import at.hollander.ibex.repository.helper.ProductAmount;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import gnu.trove.map.hash.TCustomHashMap;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "orders")
@ToString(exclude = "orders")
public class Invoice {

    @JsonView({View.Invoice.Overview.class, View.Invoice.Details.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Account account;

    @JsonView({View.Invoice.Overview.class, View.Invoice.Details.class})
    @Column(nullable = false)
    private LocalDate date;

    @JsonView({View.Invoice.Details.class})
    @Column(nullable = false)
    private String accountName;

    @JsonView({View.Invoice.Details.class})
    @Column(nullable = false)
    private String iban;

    @JsonView({View.Invoice.Details.class})
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    public Invoice(Account account, LocalDate date, String accountName, String iban) {
        this.account = account;
        this.date = date;
        this.accountName = accountName;
        this.iban = iban;
    }

    @JsonView({View.Invoice.Overview.class, View.Invoice.Details.class})
    @JsonGetter("priceTotal")
    public BigDecimal getPriceTotal() {
        return getOrders().stream().map(Order::getPriceTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @JsonView({View.Invoice.Details.class})
    @JsonGetter("amountsTotal")
    public List<ProductAmount> getAmountsTotal() {
        return getOrders().stream()
                .map(Order::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        OrderItem::getProduct,
                        () -> new TCustomHashMap<>(new Product.ProductIdHasingStrategy()),
                        Collectors.summingInt(OrderItem::getAmount)))
                .entrySet().stream()
                .map(e -> new ProductAmount(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
