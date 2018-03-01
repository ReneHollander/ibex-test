package at.hollander.ibex.entity;

import at.hollander.ibex.entity.id.CityId;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
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
    @JsonIgnore
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 60)
    @JsonIgnore
    private String password;

    @ManyToOne(optional = false)
    @JsonIgnore
    private City city;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RecurringOrder> recurringOrders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Invoice> invoices;

    @JsonGetter("city")
    public String getCityName() {
        return city.getCityId().getName();
    }

    @JsonGetter("postcode")
    public int getCityPostcode() {
        return city.getCityId().getPostcode();
    }

    @JsonSetter("city")
    public void setCityName(String name) {
        if (city == null) city = new City(new CityId());
        city.getCityId().setName(name);
    }

    @JsonSetter("postcode")
    public void setCityPostcode(int postcode) {
        if (city == null) city = new City(new CityId());
        city.getCityId().setPostcode(postcode);
    }

}
