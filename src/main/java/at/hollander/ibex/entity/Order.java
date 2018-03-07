package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "items")
@ToString(exclude = "items")
public class Order {

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Column(nullable = false)
    private LocalDateTime deliveryTime;

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Column(nullable = false)
    private LocalDateTime orderTime;

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Column(nullable = false)
    private String address;

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Column(nullable = false)
    private int postcode;

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Column(nullable = false)
    private String city;

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Column(nullable = false)
    private String deliveryNote;

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Column(nullable = false)
    private BigDecimal priceShipping;

    @JsonView({View.Order.List.class, View.Order.Overview.class})
    @Formula("(SELECT (SUM(oi.amount * oi.price_per_item) + price_shipping) FROM order_item oi WHERE oi.order_id = id)")
    private BigDecimal priceTotal;

    @JsonView({View.Order.Overview.class})
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    public Order(Account account, Invoice invoice, LocalDateTime deliveryTime, LocalDateTime orderTime, String address, int postcode, String city, String deliveryNote, BigDecimal priceShipping) {
        this.account = account;
        this.invoice = invoice;
        this.deliveryTime = deliveryTime;
        this.orderTime = orderTime;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.deliveryNote = deliveryNote;
        this.priceShipping = priceShipping;
    }
}
