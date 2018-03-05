package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "orders")
@ToString(exclude = "orders")
public class Invoice {

    @JsonView({View.Invoice.List.class, View.Invoice.Overview.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private Account account;

    @JsonView({View.Invoice.List.class, View.Invoice.Overview.class})
    @Column(nullable = false)
    private LocalDate date;

    @JsonView({View.Invoice.Overview.class})
    @Column(nullable = false)
    private String accountName;

    @JsonView({View.Invoice.Overview.class})
    @Column(nullable = false)
    private String iban;

    @JsonView({View.Invoice.Overview.class})
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    public Invoice(Account account, LocalDate date, String accountName, String iban) {
        this.account = account;
        this.date = date;
        this.accountName = accountName;
        this.iban = iban;
    }

    @JsonView({View.Invoice.List.class, View.Invoice.Overview.class})
    @JsonGetter("priceTotal")
    public BigDecimal getPriceTotal() {
        return orders.stream().map(Order::getPriceTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
