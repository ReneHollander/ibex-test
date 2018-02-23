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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Account account;

    @Column(nullable = false)
    private Status status = Status.ORDERED;

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

    public enum Status {
        ORDERED,
        DELIVERED,
        PAID,
        DISPUTED
    }
}
