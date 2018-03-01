package at.hollander.ibex.entity;

import lombok.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private Account account;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Invoice invoice;

    @Column(nullable = false)
    private LocalDateTime deliveryTime;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int postcode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String deliveryNote;

    @Column(nullable = false)
    private BigDecimal priceShipping;

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
