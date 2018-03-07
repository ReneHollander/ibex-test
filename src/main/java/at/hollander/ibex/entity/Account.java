package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recurringOrders", "orders", "invoices"})
@ToString(exclude = {"recurringOrders", "orders", "invoices"})
@Builder
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private boolean enabled = false;

    @Column(nullable = false)
    @JsonView({View.Account.Basic.class, View.Account.Details.class})
    private String name;

    @Column(unique = true, nullable = false)
    @JsonView({View.Account.Basic.class, View.Account.Details.class})
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonView({View.Account.Basic.class, View.Account.Details.class})
    private City city;

    @Column(nullable = false)
    @JsonView({View.Account.Details.class})
    private String address;

    @Column
    @JsonView({View.Account.Details.class})
    private String deliveryNote;

    @Column(nullable = false)
    @JsonView({View.Account.Details.class})
    private String accountName;

    @Column(nullable = false)
    @JsonView({View.Account.Details.class})
    private String iban;

    @Column(nullable = false)
    @JsonView({View.Account.Details.class})
    private String phone;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecurringOrder> recurringOrders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;

}
