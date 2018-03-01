package at.hollander.ibex.entity;

import lombok.*;

import javax.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private Account account;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String iban;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    public Invoice(Account account, LocalDate date, String accountName, String iban) {
        this.account = account;
        this.date = date;
        this.accountName = accountName;
        this.iban = iban;
    }
}
